/*
 * SPDX-FileCopyrightText: 2024 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package test.com.top_logic.element.structured.model;

/**
 * Factory for <code>TestTypes</code> objects.
 * 
 * <p>
 * Note: this is generated code. Do not modify. Instead, create a subclass and register this in the module system.
 * </p>
 * 
 * @author Automatically generated by {@link com.top_logic.element.model.generate.FactoryGenerator}
 */
public class TestTypesFactory extends com.top_logic.element.structured.wrap.StructuredElementWrapperFactory {

	/**
	 * Name of singleton {@link #TEST_TYPES_STRUCTURE}.
	 */
	public static final String SINGLETON_ROOT = "ROOT";

	/**
	 * Name of the structure <code>TestTypes</code> defined by {@link TestTypesFactory}.
	 */
	public static final String TEST_TYPES_STRUCTURE = "TestTypes";

	/**
	 * Lookup {@link A} type.
	 */
	public static com.top_logic.model.TLClass getAType() {
		return (com.top_logic.model.TLClass) com.top_logic.util.model.ModelService.getApplicationModel().getModule(TEST_TYPES_STRUCTURE).getType(A.A_TYPE);
	}

	/**
	 * Lookup {@link A#AS1_ATTR} of {@link A}.
	 */
	public static com.top_logic.model.TLProperty getAs1AAttr() {
		return (com.top_logic.model.TLProperty) getAType().getPart(A.AS1_ATTR);
	}

	/**
	 * Lookup {@link A#AS2_ATTR} of {@link A}.
	 */
	public static com.top_logic.model.TLProperty getAs2AAttr() {
		return (com.top_logic.model.TLProperty) getAType().getPart(A.AS2_ATTR);
	}

	/**
	 * Lookup {@link B} type.
	 */
	public static com.top_logic.model.TLClass getBType() {
		return (com.top_logic.model.TLClass) com.top_logic.util.model.ModelService.getApplicationModel().getModule(TEST_TYPES_STRUCTURE).getType(B.B_TYPE);
	}

	/**
	 * Lookup {@link B#BOOLEAN_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLProperty getBooleanBAttr() {
		return (com.top_logic.model.TLProperty) getBType().getPart(B.BOOLEAN_ATTR);
	}

	/**
	 * Lookup {@link B#BS1_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLProperty getBs1BAttr() {
		return (com.top_logic.model.TLProperty) getBType().getPart(B.BS1_ATTR);
	}

	/**
	 * Lookup {@link B#BS2_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLProperty getBs2BAttr() {
		return (com.top_logic.model.TLProperty) getBType().getPart(B.BS2_ATTR);
	}

	/**
	 * Lookup {@link B#CHECKLIST_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLReference getChecklistBAttr() {
		return (com.top_logic.model.TLReference) getBType().getPart(B.CHECKLIST_ATTR);
	}

	/**
	 * Lookup {@link B#CHECKLIST_MULTI_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLReference getChecklistMultiBAttr() {
		return (com.top_logic.model.TLReference) getBType().getPart(B.CHECKLIST_MULTI_ATTR);
	}

	/**
	 * Lookup {@link B#CHECKLIST_SINGLE_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLReference getChecklistSingleBAttr() {
		return (com.top_logic.model.TLReference) getBType().getPart(B.CHECKLIST_SINGLE_ATTR);
	}

	/**
	 * Lookup {@link B#COLLECTION_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLReference getCollectionBAttr() {
		return (com.top_logic.model.TLReference) getBType().getPart(B.COLLECTION_ATTR);
	}

	/**
	 * Lookup {@link B#DATE_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLProperty getDateBAttr() {
		return (com.top_logic.model.TLProperty) getBType().getPart(B.DATE_ATTR);
	}

	/**
	 * Lookup {@link B#DOUBLE_STRUCTURE_REVERSE_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLProperty getDoubleStructureReverseBAttr() {
		return (com.top_logic.model.TLProperty) getBType().getPart(B.DOUBLE_STRUCTURE_REVERSE_ATTR);
	}

	/**
	 * Lookup {@link B#FLOAT_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLProperty getFloatBAttr() {
		return (com.top_logic.model.TLProperty) getBType().getPart(B.FLOAT_ATTR);
	}

	/**
	 * Lookup {@link B#LIST_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLReference getListBAttr() {
		return (com.top_logic.model.TLReference) getBType().getPart(B.LIST_ATTR);
	}

	/**
	 * Lookup {@link B#LONG_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLProperty getLongBAttr() {
		return (com.top_logic.model.TLProperty) getBType().getPart(B.LONG_ATTR);
	}

	/**
	 * Lookup {@link B#MANDATORY_BOOLEAN_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLProperty getMandatoryBooleanBAttr() {
		return (com.top_logic.model.TLProperty) getBType().getPart(B.MANDATORY_BOOLEAN_ATTR);
	}

	/**
	 * Lookup {@link B#MANDATORY_CHECKLIST_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLReference getMandatoryChecklistBAttr() {
		return (com.top_logic.model.TLReference) getBType().getPart(B.MANDATORY_CHECKLIST_ATTR);
	}

	/**
	 * Lookup {@link B#MANDATORY_CHECKLIST_MULTI_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLReference getMandatoryChecklistMultiBAttr() {
		return (com.top_logic.model.TLReference) getBType().getPart(B.MANDATORY_CHECKLIST_MULTI_ATTR);
	}

	/**
	 * Lookup {@link B#MANDATORY_CHECKLIST_SINGLE_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLReference getMandatoryChecklistSingleBAttr() {
		return (com.top_logic.model.TLReference) getBType().getPart(B.MANDATORY_CHECKLIST_SINGLE_ATTR);
	}

	/**
	 * Lookup {@link B#MANDATORY_COLLECTION_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLReference getMandatoryCollectionBAttr() {
		return (com.top_logic.model.TLReference) getBType().getPart(B.MANDATORY_COLLECTION_ATTR);
	}

	/**
	 * Lookup {@link B#MANDATORY_DATE_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLProperty getMandatoryDateBAttr() {
		return (com.top_logic.model.TLProperty) getBType().getPart(B.MANDATORY_DATE_ATTR);
	}

	/**
	 * Lookup {@link B#MANDATORY_FLOAT_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLProperty getMandatoryFloatBAttr() {
		return (com.top_logic.model.TLProperty) getBType().getPart(B.MANDATORY_FLOAT_ATTR);
	}

	/**
	 * Lookup {@link B#MANDATORY_LIST_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLReference getMandatoryListBAttr() {
		return (com.top_logic.model.TLReference) getBType().getPart(B.MANDATORY_LIST_ATTR);
	}

	/**
	 * Lookup {@link B#MANDATORY_LONG_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLProperty getMandatoryLongBAttr() {
		return (com.top_logic.model.TLProperty) getBType().getPart(B.MANDATORY_LONG_ATTR);
	}

	/**
	 * Lookup {@link B#MANDATORY_STRING_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLProperty getMandatoryStringBAttr() {
		return (com.top_logic.model.TLProperty) getBType().getPart(B.MANDATORY_STRING_ATTR);
	}

	/**
	 * Lookup {@link B#MANDATORY_STRING_SET_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLProperty getMandatoryStringSetBAttr() {
		return (com.top_logic.model.TLProperty) getBType().getPart(B.MANDATORY_STRING_SET_ATTR);
	}

	/**
	 * Lookup {@link B#MANDATORY_STRUCTURE_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLReference getMandatoryStructureBAttr() {
		return (com.top_logic.model.TLReference) getBType().getPart(B.MANDATORY_STRUCTURE_ATTR);
	}

	/**
	 * Lookup {@link B#MANDATORY_TYPED_SET_ORDERED_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLReference getMandatoryTypedSetOrderedBAttr() {
		return (com.top_logic.model.TLReference) getBType().getPart(B.MANDATORY_TYPED_SET_ORDERED_ATTR);
	}

	/**
	 * Lookup {@link B#MANDATORY_TYPED_SET_UNORDERED_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLReference getMandatoryTypedSetUnorderedBAttr() {
		return (com.top_logic.model.TLReference) getBType().getPart(B.MANDATORY_TYPED_SET_UNORDERED_ATTR);
	}

	/**
	 * Lookup {@link B#MANDATORY_TYPED_WRAPPER_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLReference getMandatoryTypedWrapperBAttr() {
		return (com.top_logic.model.TLReference) getBType().getPart(B.MANDATORY_TYPED_WRAPPER_ATTR);
	}

	/**
	 * Lookup {@link B#MANDATORY_UNTYPED_WRAPPER_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLReference getMandatoryUntypedWrapperBAttr() {
		return (com.top_logic.model.TLReference) getBType().getPart(B.MANDATORY_UNTYPED_WRAPPER_ATTR);
	}

	/**
	 * Lookup {@link B#MANDATORY_WEB_FOLDER_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLReference getMandatoryWebFolderBAttr() {
		return (com.top_logic.model.TLReference) getBType().getPart(B.MANDATORY_WEB_FOLDER_ATTR);
	}

	/**
	 * Lookup {@link B#REVISION_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLReference getRevisionBAttr() {
		return (com.top_logic.model.TLReference) getBType().getPart(B.REVISION_ATTR);
	}

	/**
	 * Lookup {@link B#SIMPLE_REF_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLReference getSimpleRefBAttr() {
		return (com.top_logic.model.TLReference) getBType().getPart(B.SIMPLE_REF_ATTR);
	}

	/**
	 * Lookup {@link B#SIMPLE_SET_REF_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLReference getSimpleSetRefBAttr() {
		return (com.top_logic.model.TLReference) getBType().getPart(B.SIMPLE_SET_REF_ATTR);
	}

	/**
	 * Lookup {@link B#STRING_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLProperty getStringBAttr() {
		return (com.top_logic.model.TLProperty) getBType().getPart(B.STRING_ATTR);
	}

	/**
	 * Lookup {@link B#STRING_SET_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLProperty getStringSetBAttr() {
		return (com.top_logic.model.TLProperty) getBType().getPart(B.STRING_SET_ATTR);
	}

	/**
	 * Lookup {@link B#STRUCTURE_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLReference getStructureBAttr() {
		return (com.top_logic.model.TLReference) getBType().getPart(B.STRUCTURE_ATTR);
	}

	/**
	 * Lookup {@link B#TYPED_SET_ORDERED_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLReference getTypedSetOrderedBAttr() {
		return (com.top_logic.model.TLReference) getBType().getPart(B.TYPED_SET_ORDERED_ATTR);
	}

	/**
	 * Lookup {@link B#TYPED_SET_UNORDERED_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLReference getTypedSetUnorderedBAttr() {
		return (com.top_logic.model.TLReference) getBType().getPart(B.TYPED_SET_UNORDERED_ATTR);
	}

	/**
	 * Lookup {@link B#TYPED_WRAPPER_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLReference getTypedWrapperBAttr() {
		return (com.top_logic.model.TLReference) getBType().getPart(B.TYPED_WRAPPER_ATTR);
	}

	/**
	 * Lookup {@link B#UNTYPED_WRAPPER_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLReference getUntypedWrapperBAttr() {
		return (com.top_logic.model.TLReference) getBType().getPart(B.UNTYPED_WRAPPER_ATTR);
	}

	/**
	 * Lookup {@link B#WEB_FOLDER_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLReference getWebFolderBAttr() {
		return (com.top_logic.model.TLReference) getBType().getPart(B.WEB_FOLDER_ATTR);
	}

	/**
	 * Lookup {@link ANode} type.
	 */
	public static com.top_logic.model.TLClass getANodeType() {
		return (com.top_logic.model.TLClass) com.top_logic.util.model.ModelService.getApplicationModel().getModule(TEST_TYPES_STRUCTURE).getType(ANode.A_NODE_TYPE);
	}

	/**
	 * Lookup {@link ANode#AS1_ATTR} of {@link ANode}.
	 */
	public static com.top_logic.model.TLProperty getAs1ANodeAttr() {
		return (com.top_logic.model.TLProperty) getANodeType().getPart(ANode.AS1_ATTR);
	}

	/**
	 * Lookup {@link ANode#CHILDREN_ATTR} of {@link ANode}.
	 */
	public static com.top_logic.model.TLReference getChildrenANodeAttr() {
		return (com.top_logic.model.TLReference) getANodeType().getPart(ANode.CHILDREN_ATTR);
	}

	/**
	 * Lookup {@link ANode#NAME_ATTR} of {@link ANode}.
	 */
	public static com.top_logic.model.TLProperty getNameANodeAttr() {
		return (com.top_logic.model.TLProperty) getANodeType().getPart(ANode.NAME_ATTR);
	}

	/**
	 * Lookup {@link BNode} type.
	 */
	public static com.top_logic.model.TLClass getBNodeType() {
		return (com.top_logic.model.TLClass) com.top_logic.util.model.ModelService.getApplicationModel().getModule(TEST_TYPES_STRUCTURE).getType(BNode.B_NODE_TYPE);
	}

	/**
	 * Lookup {@link BNode#CHILDREN_ATTR} of {@link BNode}.
	 */
	public static com.top_logic.model.TLReference getChildrenBNodeAttr() {
		return (com.top_logic.model.TLReference) getBNodeType().getPart(BNode.CHILDREN_ATTR);
	}

	/**
	 * Lookup {@link BNode#HISTORIC_INLINE_REFERENCE_ATTR} of {@link BNode}.
	 */
	public static com.top_logic.model.TLReference getHistoricInlineReferenceBNodeAttr() {
		return (com.top_logic.model.TLReference) getBNodeType().getPart(BNode.HISTORIC_INLINE_REFERENCE_ATTR);
	}

	/**
	 * Lookup {@link CNode} type.
	 */
	public static com.top_logic.model.TLClass getCNodeType() {
		return (com.top_logic.model.TLClass) com.top_logic.util.model.ModelService.getApplicationModel().getModule(TEST_TYPES_STRUCTURE).getType(CNode.C_NODE_TYPE);
	}

	/**
	 * Lookup {@link CNode#COMPOSITE_REFERENCE_MULTI_ATTR} of {@link CNode}.
	 */
	public static com.top_logic.model.TLReference getCompositeReferenceMultiCNodeAttr() {
		return (com.top_logic.model.TLReference) getCNodeType().getPart(CNode.COMPOSITE_REFERENCE_MULTI_ATTR);
	}

	/**
	 * Lookup {@link CNode#HISTORIC_REFERENCE_MULTI_ATTR} of {@link CNode}.
	 */
	public static com.top_logic.model.TLReference getHistoricReferenceMultiCNodeAttr() {
		return (com.top_logic.model.TLReference) getCNodeType().getPart(CNode.HISTORIC_REFERENCE_MULTI_ATTR);
	}

	/**
	 * Lookup {@link CNode#HISTORIC_REFERENCE_SINGLE_ATTR} of {@link CNode}.
	 */
	public static com.top_logic.model.TLReference getHistoricReferenceSingleCNodeAttr() {
		return (com.top_logic.model.TLReference) getCNodeType().getPart(CNode.HISTORIC_REFERENCE_SINGLE_ATTR);
	}

	/**
	 * Lookup {@link CNode#MIXED_REFERENCE_MULTI_ATTR} of {@link CNode}.
	 */
	public static com.top_logic.model.TLReference getMixedReferenceMultiCNodeAttr() {
		return (com.top_logic.model.TLReference) getCNodeType().getPart(CNode.MIXED_REFERENCE_MULTI_ATTR);
	}

	/**
	 * Lookup {@link CNode#MIXED_REFERENCE_SINGLE_ATTR} of {@link CNode}.
	 */
	public static com.top_logic.model.TLReference getMixedReferenceSingleCNodeAttr() {
		return (com.top_logic.model.TLReference) getCNodeType().getPart(CNode.MIXED_REFERENCE_SINGLE_ATTR);
	}

	/**
	 * Lookup {@link CContent} type.
	 */
	public static com.top_logic.model.TLClass getCContentType() {
		return (com.top_logic.model.TLClass) com.top_logic.util.model.ModelService.getApplicationModel().getModule(TEST_TYPES_STRUCTURE).getType(CContent.C_CONTENT_TYPE);
	}

	/**
	 * Lookup {@link ANodeChild} type.
	 */
	public static com.top_logic.model.TLClass getANodeChildType() {
		return (com.top_logic.model.TLClass) com.top_logic.util.model.ModelService.getApplicationModel().getModule(TEST_TYPES_STRUCTURE).getType(ANodeChild.A_NODE_CHILD_TYPE);
	}

	/**
	 * Lookup {@link BNodeChild} type.
	 */
	public static com.top_logic.model.TLClass getBNodeChildType() {
		return (com.top_logic.model.TLClass) com.top_logic.util.model.ModelService.getApplicationModel().getModule(TEST_TYPES_STRUCTURE).getType(BNodeChild.B_NODE_CHILD_TYPE);
	}

	/**
	 * Name of type <code>A</code> in structure {@link #TEST_TYPES_STRUCTURE}.
	 * 
	 * @deprecated Use {@link A#A_TYPE}.
	 */
	@Deprecated
	public static final String A_NODE = A.A_TYPE;

	/**
	 * Name of type <code>B</code> in structure {@link #TEST_TYPES_STRUCTURE}.
	 * 
	 * @deprecated Use {@link B#B_TYPE}.
	 */
	@Deprecated
	public static final String B_NODE = B.B_TYPE;

	/**
	 * Name of type <code>ANode</code> in structure {@link #TEST_TYPES_STRUCTURE}.
	 * 
	 * @deprecated Use {@link ANode#A_NODE_TYPE}.
	 */
	@Deprecated
	public static final String A_NODE_NODE = ANode.A_NODE_TYPE;

	/**
	 * Storage table name of {@link #A_NODE_NODE} objects.
	 */
	public static final String KO_NAME_A_NODE = "StructuredElement";

	/**
	 * Name of type <code>BNode</code> in structure {@link #TEST_TYPES_STRUCTURE}.
	 * 
	 * @deprecated Use {@link BNode#B_NODE_TYPE}.
	 */
	@Deprecated
	public static final String B_NODE_NODE = BNode.B_NODE_TYPE;

	/**
	 * Storage table name of {@link #B_NODE_NODE} objects.
	 */
	public static final String KO_NAME_B_NODE = "BElement";

	/**
	 * Name of type <code>CNode</code> in structure {@link #TEST_TYPES_STRUCTURE}.
	 * 
	 * @deprecated Use {@link CNode#C_NODE_TYPE}.
	 */
	@Deprecated
	public static final String C_NODE_NODE = CNode.C_NODE_TYPE;

	/**
	 * Storage table name of {@link #C_NODE_NODE} objects.
	 */
	public static final String KO_NAME_C_NODE = "StructuredElement";

	/**
	 * Name of type <code>CContent</code> in structure {@link #TEST_TYPES_STRUCTURE}.
	 * 
	 * @deprecated Use {@link CContent#C_CONTENT_TYPE}.
	 */
	@Deprecated
	public static final String C_CONTENT_NODE = CContent.C_CONTENT_TYPE;

	/**
	 * Storage table name of {@link #C_CONTENT_NODE} objects.
	 */
	public static final String KO_NAME_C_CONTENT = "GenericObject";

	/**
	 * Name of type <code>ANodeChild</code> in structure {@link #TEST_TYPES_STRUCTURE}.
	 * 
	 * @deprecated Use {@link ANodeChild#A_NODE_CHILD_TYPE}.
	 */
	@Deprecated
	public static final String A_NODE_CHILD_NODE = ANodeChild.A_NODE_CHILD_TYPE;

	/**
	 * Name of type <code>BNodeChild</code> in structure {@link #TEST_TYPES_STRUCTURE}.
	 * 
	 * @deprecated Use {@link BNodeChild#B_NODE_CHILD_TYPE}.
	 */
	@Deprecated
	public static final String B_NODE_CHILD_NODE = BNodeChild.B_NODE_CHILD_TYPE;

	/**
	 * Singleton <code>ROOT</code>.
	 */
	public ANode getRootSingleton() {
		return (ANode) lookupSingleton(SINGLETON_ROOT);
	}

	/**
	 * Singleton <code>ROOT</code> on given branch.
	 */
	public ANode getRootSingleton(com.top_logic.knowledge.service.Branch requestedBranch) {
		return (ANode) lookupSingleton(SINGLETON_ROOT, requestedBranch);
	}

	/**
	 * Singleton <code>ROOT</code> on given branch in given revision.
	 */
	public ANode getRootSingleton(com.top_logic.knowledge.service.Branch requestedBranch, com.top_logic.knowledge.service.Revision historyContext) {
		return (ANode) lookupSingleton(SINGLETON_ROOT, requestedBranch, historyContext);
	}


	/**
	 * Create an instance of {@link ANode} type.
	 */
	public final ANode createANode(com.top_logic.model.TLObject context) {
		return (ANode) createObject(getANodeType(), context);
	}

	/**
	 * Create an instance of {@link ANode} type.
	 */
	public final ANode createANode() {
		return createANode(null);
	}

	/**
	 * Create an instance of {@link BNode} type.
	 */
	public final BNode createBNode(com.top_logic.model.TLObject context) {
		return (BNode) createObject(getBNodeType(), context);
	}

	/**
	 * Create an instance of {@link BNode} type.
	 */
	public final BNode createBNode() {
		return createBNode(null);
	}

	/**
	 * Create an instance of {@link CNode} type.
	 */
	public final CNode createCNode(com.top_logic.model.TLObject context) {
		return (CNode) createObject(getCNodeType(), context);
	}

	/**
	 * Create an instance of {@link CNode} type.
	 */
	public final CNode createCNode() {
		return createCNode(null);
	}

	/**
	 * Create an instance of {@link CContent} type.
	 */
	public final CContent createCContent(com.top_logic.model.TLObject context) {
		return (CContent) createObject(getCContentType(), context);
	}

	/**
	 * Create an instance of {@link CContent} type.
	 */
	public final CContent createCContent() {
		return createCContent(null);
	}

	/**
	 * The singleton instance of {@link TestTypesFactory}.
	 */
	public static TestTypesFactory getInstance() {
		return (TestTypesFactory) com.top_logic.element.model.DynamicModelService.getFactoryFor(TEST_TYPES_STRUCTURE);
	}
}
