/*
 * SPDX-FileCopyrightText: 2001 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.base.user.douser;


import com.top_logic.base.security.attributes.PersonAttributes;
import com.top_logic.base.security.authorisation.roles.ACL;
import com.top_logic.base.user.UserInterface;
import com.top_logic.basic.Logger;
import com.top_logic.basic.TLID;
import com.top_logic.dob.DataObject;
import com.top_logic.dob.DataObjectException;
import com.top_logic.dob.MOAttribute;
import com.top_logic.dob.MetaObject;
import com.top_logic.dob.ex.NoSuchAttributeException;
import com.top_logic.dob.identifier.ObjectKey;
import com.top_logic.dob.meta.MOReference;

/**
 * This class is an implementation of Userinterface and represents a 
 * userobject in top-logic.
 *
 * In fact this class just encapsules an dataobject and provides methods
 * to simplyfy access to its attributes.
 * The roles in form of an ACL will be cached in this instance, so changes
 * in the underlying data object will not be automatically checked.
 *
 * This class <b>does NOT</b> represent the <b>Person</b> KnowledgeObject.
 * 
 * @author    <a href="mailto:tri@top-logic.com">Thomas Richter</a>
 */
public class DOUser implements UserInterface, PersonAttributes {

	/**
	 * @see com.top_logic.base.user.UserInterface#getDataAccessDeviceID()
	 */
	@Override
	public String getDataAccessDeviceID() {
		
		try {
			return (String) this.internalUser.getAttributeValue(PersonAttributes.DATA_ACCESS_DEVICE_ID);
		} catch (NoSuchAttributeException e) {
			Logger.error("Problem getting deviceId from userDo: "+internalUser,e,this);
			return null;
		}
	}

    /** The internal dataobject. */
    protected DataObject internalUser;


    /** The ACL of the user. */
    private ACL acl;    // Private - must by synchronized

    /**
     * Creates a new DOUser.
     *
     * @param    aUser    The user as DataObject.
     */
    protected DOUser (DataObject aUser) {
        this.internalUser = aUser;
    }

    @Override
	public String toString() {
        return this.getClass().toString() + '[' + this.getUserName () + ']';
    }
 
    /**
     * Reset the user attributes, i.e. re-read from given DataObject.
     */
    public void reset (DataObject aUser) {
        this.internalUser = aUser;
        this.resetACLRoles();
    }

    /**
     * Get or create the DOUser with the given user data.
     * 
     * @param   aUserDO the user data.
     * @return  the existing or new user, null on error
     */
    public static synchronized DOUser getInstance (DataObject aUserDO) {
            return new DOUser (aUserDO);
    }

//    /** equals() based on key or internalUser */
//    public boolean equals (Object anObject) {
//        if (anObject instanceof DOUser) {
//            return (this.key.equals (((DOUser) anObject).key));
//        }
//        else if (anObject instanceof DataObject) {
//            return (this.internalUser.equals (anObject));
//        }
//        else {
//            return false;
//        }
//    }
//
//    /** Extarct hashCode from key */
//    public int hashCode () {
//        return (this.key.hashCode ());
//    }

    /**
     * getter for username, i.e. the login-name.
     * As such this is the unique ID of this user  which is the same
     * as Person::getName() for the according person.
     * This name / id connects a user to a person ! 
     * @return the username of this user
     */
    @Override
	public String getUserName () {
        try {
            return (String) internalUser.getAttributeValue (USER_NAME);
        }
        catch (NoSuchAttributeException nae) {
            Logger.error ("Tried to retrieve Users USERNAME from dataobject " + internalUser + ", which is no User or has no such attribute", this);
            return "";
        }
    }    

    /**
    * the firstname of this user
    */
    @Override
	public String getFirstName () {
        try {
            return(String)(internalUser.getAttributeValue (GIVEN_NAME));                
        }
        catch (NoSuchAttributeException nae) {
            Logger.error ("Tried to retrieve Users GIVENNAME from dataobject " + internalUser + ", which is no User or has no such attribute", this);
            return "";
        }
    }    

    /**
    * the lastname of this user
    */
    @Override
	public String getLastName () {
        try {
            return(String)(internalUser.getAttributeValue (SUR_NAME));                
        }catch (NoSuchAttributeException nae) {
            Logger.error ("Tried to retrieve Users SUR_NAME from dataobject " + internalUser + ", which is no User or has no such attribute", this);
            return "";
        }
    }


    /**
     * get formatted username: Title Firstname Lastname. 
     * @return the fullname of this user
     */
    @Override
	public String getFullName () {
        try {
            return(String)(internalUser.getAttributeValue (DISPLAY_NAME));
        }catch (NoSuchAttributeException nae) {
            Logger.error ("Tried to retrieve Users DISPLAYNAME from dataobject " + internalUser + ", which is no User or has no such attribute", this);
            return "";
        }
    }

	/**
	 * get formatted username: Lastname, Title Firstname.
	 * Suppress Title via param includeTitle
	 * 
	 * @param  includeTitle true to include, false to suppress 
	 * @return formatted username
	 */
	@Override
	public String getNameAs_LastTitleFirst (boolean includeTitle) {
		
		StringBuffer name = new StringBuffer();
		name.append(getLastName()).append(", ");
		if (includeTitle) {
			name.append(getTitle()).append(' ');
		}
		name.append(getFirstName());
		return name.toString();
	}

	/**
	 * Is this user restricted or not.
	 * 
	 * @see com.top_logic.base.user.UserInterface#isRestricted()
	 */
	@Override
	public Boolean isRestricted() {
		try {
			return (Boolean) (internalUser.getAttributeValue(RESTRICTED_USER));
		} catch (NoSuchAttributeException nae) {
			Logger.error("Tried to retrieve Users RESTRICTION from dataobject " + internalUser
				+ ", which is no User or has no such attribute", DOUser.class);
			return false;
		}
	}

    /**
    * the roles of this user as string, sparated by ','
    */
    @Override
	public String getRoleString () {
        try {
            return (String)this.internalUser.getAttributeValue (USER_ROLE);                            
        }catch (NoSuchAttributeException nae) {
            Logger.error ("Tried to retrieve Userroles from dataobject " + internalUser + ", which is no User or has no such attribute", this);
            return "";
        }
    }

    /**
     * Returns the roles of this user as ACL.
     *
     * This value will be cached in this instance, so that changes in the
     * underlying data object will not be checked.
     *
     * @return    The roles of this user as ACL.
     */
    @Override
	public synchronized ACL getACLRoles () {
        if (this.acl == null) {
            try {
                String theName   = this.getUserName ();
                String theString = (String) (this.internalUser.
                                            getAttributeValue (USER_ROLE));

                this.acl = new ACL(theString);

				// Make sure that user is contained in its own Role List.
               	this.acl.addRole(theName);
            }
            catch (NoSuchAttributeException ex) {
                Logger.error ("Unable to get user role from data object", 
                              ex, this);
            }
        }

        return (this.acl);
    }

    /** 
     * Method for resetting the cached ACL roles.
     */
    public synchronized void resetACLRoles () {
        this.acl = null;
    }

    /**
     * the object class of this user (should be always "person")
     */
    @Override
	public String getObjectClass () {

        try {
            return(String)(internalUser.getAttributeValue (OBJECT_CLASS));                
        }catch (NoSuchAttributeException nae) {
            Logger.error ("Tried to retrieve OBJECT_CLASS from dataobject " + internalUser + ", which is no User or has no such attribute", this);
            return "";
        }
    }

    /**
     * the password of this user
     */
    @Override
	public String getPassword () {

        try {
            return(String)(internalUser.getAttributeValue (PASSWORD));                
        }catch (NoSuchAttributeException nae) {
            Logger.error ("Tried to retrieve Users PASSWORD from dataobject " + internalUser + ", which is no User or has no such attribute", this);
            return "";
        }
    }


    /**
     * the internal email of this user
     */
    @Override
	public String getInternalMail () {

        try {
            return(String)(internalUser.getAttributeValue (MAIL_NAME));                
        }catch (NoSuchAttributeException nae) {
            Logger.error ("Tried to retrieve Users MAIL_NAME from dataobject " + internalUser + ", which is no User or has no such attribute", this);
            return "";
        }
    }

    /**
     * the external email of this user
     */
    @Override
	public String getExternalMail () {

        try {
            return(String)(internalUser.getAttributeValue (EXTERNAL_MAIL));                
        }catch (NoSuchAttributeException nae) {
            Logger.error ("Tried to retrieve EXTERNAL_MAIL from dataobject " + internalUser + ", which is no User or has no such attribute", this);
            return "";
        }
    }

    /**
     * the customer name of this user
     */
    @Override
	public String getCustomerName () {

        try {
            return(String)(internalUser.getAttributeValue (CUSTOMER));                
        }catch (NoSuchAttributeException nae) {
            Logger.error ("Tried to retrieve Users CUSTOMER from dataobject " + internalUser + ", which is no User or has no such attribute", this);
            return "";
        }
    }

    /**
     * the title of this user
     */
    @Override
	public String getTitle () {

        try {
            return(String)(internalUser.getAttributeValue (TITLE));                
        }catch (NoSuchAttributeException nae) {
            Logger.error ("Tried to retrieve Users TITLE from dataobject " + internalUser + ", which is no User or has no such attribute", this);
            return "";
        }
    }

    /**
     * the organization unit of this user
     */
    @Override
	public String getOrgUnit () {

        try {
            return(String)(internalUser.getAttributeValue (ORG_UNIT));                
        }catch (NoSuchAttributeException nae) {
            Logger.error ("Tried to retrieve Users ORG_UNIT from dataobject " + internalUser + ", which is no User or has no such attribute", this);
            return "";
        }
    }

    /**
     * the internal number of this user
     */
    @Override
	public String getInternalNumber () {

        try {
            return(String)(internalUser.getAttributeValue (INTERNAL_NR));                
        }catch (NoSuchAttributeException nae) {
            Logger.error ("Tried to retrieve Users INTERNAL_NUMBER from dataobject " + internalUser + ", which is no User or has no such attribute", this);
            return "";
        }
    }

    /**
     * the external number of this user
     */
    @Override
	public String getExternalNumber () {

        try {
            return(String)(internalUser.getAttributeValue (EXTERNAL_NR));                
        }catch (NoSuchAttributeException nae) {
            Logger.error ("Tried to retrieve Users EXTERNAL_NUMBER from dataobject " + internalUser + ", which is no User or has no such attribute", this);
            return "";
        }
    }

    /**
     * the mobile number of this user
     */
    @Override
	public String getMobileNumber () {

        try {
            return(String)(internalUser.getAttributeValue (MOBILE_NR));                
        }catch (NoSuchAttributeException nae) {
            Logger.error ("Tried to retrieve Users MOBILE_NUMBER from dataobject " + internalUser + ", which is no User or has no such attribute", this);
            return "";
        }
    }

    /**
     * the private number of this user
    */
    @Override
	public String getPrivateNumber () {
        try {
            return(String)(internalUser.getAttributeValue (PRIVATE_NR));                
        }
        catch (NoSuchAttributeException nae) {
            Logger.error ("Tried to retrieve Users PRIVATE_NUMBER from dataobject " + internalUser + ", which is no User or has no such attribute", this);
            return "";
        }
    }

    /**
	 * TODO #2829: Delete TL 6 deprecation.
	 * 
	 * @deprecated Use {@link #tTable()} instead
	 */
	@Override
	@Deprecated
	public final MetaObject getMetaObject() {
		return tTable();
	}

	/**
     * available. All calls to this methods will be mapped
     * to the encapsuled DataObject. Please refer the Documentation
     * of DataObject for further information about these methods.
     */
    @Override
	public MetaObject tTable () {
        return this.internalUser.tTable ();
    }

    @Override
	public boolean isInstanceOf (MetaObject aMetaObject) {
        return this.internalUser.isInstanceOf (aMetaObject);
    }

    /** Forward to the internalUser */
    @Override
	public boolean isInstanceOf (String aMetaObjectName) {
        return this.internalUser.isInstanceOf (aMetaObjectName);
    }

    @Override
	public TLID getIdentifier () {
        return this.internalUser.getIdentifier ();
    }

    @Override
	public void setIdentifier (TLID anIdentifier) {
        this.internalUser.setIdentifier (anIdentifier);
    }

    @Override
	public Iterable<? extends MOAttribute> getAttributes () {
        return this.internalUser.getAttributes ();
    }

    @Override
	public String [] getAttributeNames () {
        return this.internalUser.getAttributeNames ();
    }

	@Override
	public boolean hasAttribute(String attributeName) {
		return internalUser.hasAttribute(attributeName);
	}

    @Override
	public Object getAttributeValue (String attrName)
    throws NoSuchAttributeException {
        return this.internalUser.getAttributeValue (attrName);
    }

    @Override
	public Object setAttributeValue (String attrName, Object value) throws DataObjectException {
    	return this.internalUser.setAttributeValue(attrName,value);                
    }

    /**
     * Returns the DataObject for this user.
     *
     * @return the encapsulated DataObject (if any)
     */
    @Override
	public DataObject getDataObject () {
        return this.internalUser;
    }

	@Override
	public Object getValue(MOAttribute attribute) {
		return internalUser.getValue(attribute);
	}

	@Override
	public ObjectKey getReferencedKey(MOReference reference) {
		return internalUser.getReferencedKey(reference);
	}

	@Override
	public Object setValue(MOAttribute attribute, Object newValue) throws DataObjectException {
		return internalUser.setValue(attribute, newValue);
	}

}
