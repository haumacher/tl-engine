/*
 * SPDX-FileCopyrightText: 2024 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.demo.model.aspect;

/**
 * Factory for <code>tl.demo.aspect</code> objects.
 * 
 * <p>
 * Note: this is generated code. Do not modify. Instead, create a subclass and register this in the module system.
 * </p>
 * 
 * @author Automatically generated by {@link com.top_logic.element.model.generate.FactoryGenerator}
 */
public class TlDemoAspectFactory extends com.top_logic.element.structured.wrap.StructuredElementWrapperFactory {

	/**
	 * Name of singleton {@link #TL_DEMO_ASPECT_STRUCTURE}.
	 */
	public static final String SINGLETON_ROOT = "ROOT";

	/**
	 * Name of the structure <code>tl.demo.aspect</code> defined by {@link TlDemoAspectFactory}.
	 */
	public static final String TL_DEMO_ASPECT_STRUCTURE = "tl.demo.aspect";

	/**
	 * Lookup {@link Common} type.
	 */
	public static com.top_logic.model.TLClass getCommonType() {
		return (com.top_logic.model.TLClass) com.top_logic.util.model.ModelService.getApplicationModel().getModule(TL_DEMO_ASPECT_STRUCTURE).getType(Common.COMMON_TYPE);
	}

	/**
	 * Lookup {@link Common#CHILDREN_ATTR} of {@link Common}.
	 */
	public static com.top_logic.model.TLReference getChildrenCommonAttr() {
		return (com.top_logic.model.TLReference) getCommonType().getPart(Common.CHILDREN_ATTR);
	}

	/**
	 * Lookup {@link Common#NAME_ATTR} of {@link Common}.
	 */
	public static com.top_logic.model.TLProperty getNameCommonAttr() {
		return (com.top_logic.model.TLProperty) getCommonType().getPart(Common.NAME_ATTR);
	}

	/**
	 * Lookup {@link Common#OVERRIDE_DIAMOND_ATTR} of {@link Common}.
	 */
	public static com.top_logic.model.TLReference getOverrideDiamondCommonAttr() {
		return (com.top_logic.model.TLReference) getCommonType().getPart(Common.OVERRIDE_DIAMOND_ATTR);
	}

	/**
	 * Lookup {@link A} type.
	 */
	public static com.top_logic.model.TLClass getAType() {
		return (com.top_logic.model.TLClass) com.top_logic.util.model.ModelService.getApplicationModel().getModule(TL_DEMO_ASPECT_STRUCTURE).getType(A.A_TYPE);
	}

	/**
	 * Lookup {@link A#LONG_ATTR} of {@link A}.
	 */
	public static com.top_logic.model.TLProperty getLongAAttr() {
		return (com.top_logic.model.TLProperty) getAType().getPart(A.LONG_ATTR);
	}

	/**
	 * Lookup {@link A#OVERRIDE_ATTR} of {@link A}.
	 */
	public static com.top_logic.model.TLReference getOverrideAAttr() {
		return (com.top_logic.model.TLReference) getAType().getPart(A.OVERRIDE_ATTR);
	}

	/**
	 * Lookup {@link A#OVERRIDE_COLLECTION_ATTR} of {@link A}.
	 */
	public static com.top_logic.model.TLReference getOverrideCollectionAAttr() {
		return (com.top_logic.model.TLReference) getAType().getPart(A.OVERRIDE_COLLECTION_ATTR);
	}

	/**
	 * Lookup {@link A#OVERRIDE_DERIVED_DIFFERENT_ALGORITHM_ATTR} of {@link A}.
	 */
	public static com.top_logic.model.TLReference getOverrideDerivedDifferentAlgorithmAAttr() {
		return (com.top_logic.model.TLReference) getAType().getPart(A.OVERRIDE_DERIVED_DIFFERENT_ALGORITHM_ATTR);
	}

	/**
	 * Lookup {@link A#OVERRIDE_DERIVED_SAME_ALGORITHM_ATTR} of {@link A}.
	 */
	public static com.top_logic.model.TLReference getOverrideDerivedSameAlgorithmAAttr() {
		return (com.top_logic.model.TLReference) getAType().getPart(A.OVERRIDE_DERIVED_SAME_ALGORITHM_ATTR);
	}

	/**
	 * Lookup {@link A#OVERRIDE_DIAMOND_ATTR} of {@link A}.
	 */
	public static com.top_logic.model.TLReference getOverrideDiamondAAttr() {
		return (com.top_logic.model.TLReference) getAType().getPart(A.OVERRIDE_DIAMOND_ATTR);
	}

	/**
	 * Lookup {@link B} type.
	 */
	public static com.top_logic.model.TLClass getBType() {
		return (com.top_logic.model.TLClass) com.top_logic.util.model.ModelService.getApplicationModel().getModule(TL_DEMO_ASPECT_STRUCTURE).getType(B.B_TYPE);
	}

	/**
	 * Lookup {@link B#DATE_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLProperty getDateBAttr() {
		return (com.top_logic.model.TLProperty) getBType().getPart(B.DATE_ATTR);
	}

	/**
	 * Lookup {@link B#OVERRIDE_DIAMOND_ATTR} of {@link B}.
	 */
	public static com.top_logic.model.TLReference getOverrideDiamondBAttr() {
		return (com.top_logic.model.TLReference) getBType().getPart(B.OVERRIDE_DIAMOND_ATTR);
	}

	/**
	 * Lookup {@link C} type.
	 */
	public static com.top_logic.model.TLClass getCType() {
		return (com.top_logic.model.TLClass) com.top_logic.util.model.ModelService.getApplicationModel().getModule(TL_DEMO_ASPECT_STRUCTURE).getType(C.C_TYPE);
	}

	/**
	 * Lookup {@link C#BOOLEAN_ATTR} of {@link C}.
	 */
	public static com.top_logic.model.TLProperty getBooleanCAttr() {
		return (com.top_logic.model.TLProperty) getCType().getPart(C.BOOLEAN_ATTR);
	}

	/**
	 * Lookup {@link C#OVERRIDE_ATTR} of {@link C}.
	 */
	public static com.top_logic.model.TLReference getOverrideCAttr() {
		return (com.top_logic.model.TLReference) getCType().getPart(C.OVERRIDE_ATTR);
	}

	/**
	 * Lookup {@link C#OVERRIDE_COLLECTION_ATTR} of {@link C}.
	 */
	public static com.top_logic.model.TLReference getOverrideCollectionCAttr() {
		return (com.top_logic.model.TLReference) getCType().getPart(C.OVERRIDE_COLLECTION_ATTR);
	}

	/**
	 * Lookup {@link C#OVERRIDE_DERIVED_DIFFERENT_ALGORITHM_ATTR} of {@link C}.
	 */
	public static com.top_logic.model.TLReference getOverrideDerivedDifferentAlgorithmCAttr() {
		return (com.top_logic.model.TLReference) getCType().getPart(C.OVERRIDE_DERIVED_DIFFERENT_ALGORITHM_ATTR);
	}

	/**
	 * Lookup {@link C#OVERRIDE_DERIVED_SAME_ALGORITHM_ATTR} of {@link C}.
	 */
	public static com.top_logic.model.TLReference getOverrideDerivedSameAlgorithmCAttr() {
		return (com.top_logic.model.TLReference) getCType().getPart(C.OVERRIDE_DERIVED_SAME_ALGORITHM_ATTR);
	}

	/**
	 * Lookup {@link C#OVERRIDE_DIAMOND_ATTR} of {@link C}.
	 */
	public static com.top_logic.model.TLReference getOverrideDiamondCAttr() {
		return (com.top_logic.model.TLReference) getCType().getPart(C.OVERRIDE_DIAMOND_ATTR);
	}

	/**
	 * Lookup {@link CDefaults} type.
	 */
	public static com.top_logic.model.TLClass getCDefaultsType() {
		return (com.top_logic.model.TLClass) com.top_logic.util.model.ModelService.getApplicationModel().getModule(TL_DEMO_ASPECT_STRUCTURE).getType(CDefaults.C_DEFAULTS_TYPE);
	}

	/**
	 * Lookup {@link CDefaults#BOOLEAN_WITH_DEFAULT_ATTR} of {@link CDefaults}.
	 */
	public static com.top_logic.model.TLProperty getBooleanWithDefaultCDefaultsAttr() {
		return (com.top_logic.model.TLProperty) getCDefaultsType().getPart(CDefaults.BOOLEAN_WITH_DEFAULT_ATTR);
	}

	/**
	 * Lookup {@link CDefaults#DATE_WITH_DEFAULT_ATTR} of {@link CDefaults}.
	 */
	public static com.top_logic.model.TLProperty getDateWithDefaultCDefaultsAttr() {
		return (com.top_logic.model.TLProperty) getCDefaultsType().getPart(CDefaults.DATE_WITH_DEFAULT_ATTR);
	}

	/**
	 * Lookup {@link CDefaults#LONG_WITH_DEFAULT_ATTR} of {@link CDefaults}.
	 */
	public static com.top_logic.model.TLProperty getLongWithDefaultCDefaultsAttr() {
		return (com.top_logic.model.TLProperty) getCDefaultsType().getPart(CDefaults.LONG_WITH_DEFAULT_ATTR);
	}

	/**
	 * Lookup {@link RootNode} type.
	 */
	public static com.top_logic.model.TLClass getRootNodeType() {
		return (com.top_logic.model.TLClass) com.top_logic.util.model.ModelService.getApplicationModel().getModule(TL_DEMO_ASPECT_STRUCTURE).getType(RootNode.ROOT_NODE_TYPE);
	}

	/**
	 * Lookup {@link RootNode#NAME_ATTR} of {@link RootNode}.
	 */
	public static com.top_logic.model.TLProperty getNameRootNodeAttr() {
		return (com.top_logic.model.TLProperty) getRootNodeType().getPart(RootNode.NAME_ATTR);
	}

	/**
	 * Lookup {@link ANode} type.
	 */
	public static com.top_logic.model.TLClass getANodeType() {
		return (com.top_logic.model.TLClass) com.top_logic.util.model.ModelService.getApplicationModel().getModule(TL_DEMO_ASPECT_STRUCTURE).getType(ANode.A_NODE_TYPE);
	}

	/**
	 * Lookup {@link BNode} type.
	 */
	public static com.top_logic.model.TLClass getBNodeType() {
		return (com.top_logic.model.TLClass) com.top_logic.util.model.ModelService.getApplicationModel().getModule(TL_DEMO_ASPECT_STRUCTURE).getType(BNode.B_NODE_TYPE);
	}

	/**
	 * Lookup {@link CNode} type.
	 */
	public static com.top_logic.model.TLClass getCNodeType() {
		return (com.top_logic.model.TLClass) com.top_logic.util.model.ModelService.getApplicationModel().getModule(TL_DEMO_ASPECT_STRUCTURE).getType(CNode.C_NODE_TYPE);
	}

	/**
	 * Lookup {@link CNodeDefaults} type.
	 */
	public static com.top_logic.model.TLClass getCNodeDefaultsType() {
		return (com.top_logic.model.TLClass) com.top_logic.util.model.ModelService.getApplicationModel().getModule(TL_DEMO_ASPECT_STRUCTURE).getType(CNodeDefaults.C_NODE_DEFAULTS_TYPE);
	}

	/**
	 * Lookup {@link CommonChild} type.
	 */
	public static com.top_logic.model.TLClass getCommonChildType() {
		return (com.top_logic.model.TLClass) com.top_logic.util.model.ModelService.getApplicationModel().getModule(TL_DEMO_ASPECT_STRUCTURE).getType(CommonChild.COMMON_CHILD_TYPE);
	}

	/**
	 * Lookup {@link CommonChild#PARENT_ATTR} of {@link CommonChild}.
	 */
	public static com.top_logic.model.TLReference getParentCommonChildAttr() {
		return (com.top_logic.model.TLReference) getCommonChildType().getPart(CommonChild.PARENT_ATTR);
	}

	/**
	 * Name of type <code>Common</code> in structure {@link #TL_DEMO_ASPECT_STRUCTURE}.
	 * 
	 * @deprecated Use {@link Common#COMMON_TYPE}.
	 */
	@Deprecated
	public static final String COMMON_NODE = Common.COMMON_TYPE;

	/**
	 * Name of type <code>A</code> in structure {@link #TL_DEMO_ASPECT_STRUCTURE}.
	 * 
	 * @deprecated Use {@link A#A_TYPE}.
	 */
	@Deprecated
	public static final String A_NODE = A.A_TYPE;

	/**
	 * Name of type <code>B</code> in structure {@link #TL_DEMO_ASPECT_STRUCTURE}.
	 * 
	 * @deprecated Use {@link B#B_TYPE}.
	 */
	@Deprecated
	public static final String B_NODE = B.B_TYPE;

	/**
	 * Name of type <code>C</code> in structure {@link #TL_DEMO_ASPECT_STRUCTURE}.
	 * 
	 * @deprecated Use {@link C#C_TYPE}.
	 */
	@Deprecated
	public static final String C_NODE = C.C_TYPE;

	/**
	 * Name of type <code>CDefaults</code> in structure {@link #TL_DEMO_ASPECT_STRUCTURE}.
	 * 
	 * @deprecated Use {@link CDefaults#C_DEFAULTS_TYPE}.
	 */
	@Deprecated
	public static final String C_DEFAULTS_NODE = CDefaults.C_DEFAULTS_TYPE;

	/**
	 * Name of type <code>RootNode</code> in structure {@link #TL_DEMO_ASPECT_STRUCTURE}.
	 * 
	 * @deprecated Use {@link RootNode#ROOT_NODE_TYPE}.
	 */
	@Deprecated
	public static final String ROOT_NODE_NODE = RootNode.ROOT_NODE_TYPE;

	/**
	 * Storage table name of {@link #ROOT_NODE_NODE} objects.
	 */
	public static final String KO_NAME_ROOT_NODE = "DemoAspect";

	/**
	 * Name of type <code>ANode</code> in structure {@link #TL_DEMO_ASPECT_STRUCTURE}.
	 * 
	 * @deprecated Use {@link ANode#A_NODE_TYPE}.
	 */
	@Deprecated
	public static final String A_NODE_NODE = ANode.A_NODE_TYPE;

	/**
	 * Storage table name of {@link #A_NODE_NODE} objects.
	 */
	public static final String KO_NAME_A_NODE = "DemoAspect";

	/**
	 * Name of type <code>BNode</code> in structure {@link #TL_DEMO_ASPECT_STRUCTURE}.
	 * 
	 * @deprecated Use {@link BNode#B_NODE_TYPE}.
	 */
	@Deprecated
	public static final String B_NODE_NODE = BNode.B_NODE_TYPE;

	/**
	 * Storage table name of {@link #B_NODE_NODE} objects.
	 */
	public static final String KO_NAME_B_NODE = "DemoAspect";

	/**
	 * Name of type <code>CNode</code> in structure {@link #TL_DEMO_ASPECT_STRUCTURE}.
	 * 
	 * @deprecated Use {@link CNode#C_NODE_TYPE}.
	 */
	@Deprecated
	public static final String C_NODE_NODE = CNode.C_NODE_TYPE;

	/**
	 * Storage table name of {@link #C_NODE_NODE} objects.
	 */
	public static final String KO_NAME_C_NODE = "DemoAspect";

	/**
	 * Name of type <code>CNodeDefaults</code> in structure {@link #TL_DEMO_ASPECT_STRUCTURE}.
	 * 
	 * @deprecated Use {@link CNodeDefaults#C_NODE_DEFAULTS_TYPE}.
	 */
	@Deprecated
	public static final String C_NODE_DEFAULTS_NODE = CNodeDefaults.C_NODE_DEFAULTS_TYPE;

	/**
	 * Storage table name of {@link #C_NODE_DEFAULTS_NODE} objects.
	 */
	public static final String KO_NAME_C_NODE_DEFAULTS = "DemoAspect";

	/**
	 * Name of type <code>CommonChild</code> in structure {@link #TL_DEMO_ASPECT_STRUCTURE}.
	 * 
	 * @deprecated Use {@link CommonChild#COMMON_CHILD_TYPE}.
	 */
	@Deprecated
	public static final String COMMON_CHILD_NODE = CommonChild.COMMON_CHILD_TYPE;

	/**
	 * Singleton <code>ROOT</code>.
	 */
	public RootNode getRootSingleton() {
		return (RootNode) lookupSingleton(SINGLETON_ROOT);
	}

	/**
	 * Singleton <code>ROOT</code> on given branch.
	 */
	public RootNode getRootSingleton(com.top_logic.knowledge.service.Branch requestedBranch) {
		return (RootNode) lookupSingleton(SINGLETON_ROOT, requestedBranch);
	}

	/**
	 * Singleton <code>ROOT</code> on given branch in given revision.
	 */
	public RootNode getRootSingleton(com.top_logic.knowledge.service.Branch requestedBranch, com.top_logic.knowledge.service.Revision historyContext) {
		return (RootNode) lookupSingleton(SINGLETON_ROOT, requestedBranch, historyContext);
	}


	/**
	 * Create an instance of {@link RootNode} type.
	 */
	public final RootNode createRootNode(com.top_logic.model.TLObject context) {
		return (RootNode) createObject(getRootNodeType(), context);
	}

	/**
	 * Create an instance of {@link RootNode} type.
	 */
	public final RootNode createRootNode() {
		return createRootNode(null);
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
	 * Create an instance of {@link CNodeDefaults} type.
	 */
	public final CNodeDefaults createCNodeDefaults(com.top_logic.model.TLObject context) {
		return (CNodeDefaults) createObject(getCNodeDefaultsType(), context);
	}

	/**
	 * Create an instance of {@link CNodeDefaults} type.
	 */
	public final CNodeDefaults createCNodeDefaults() {
		return createCNodeDefaults(null);
	}

	/**
	 * The singleton instance of {@link TlDemoAspectFactory}.
	 */
	public static TlDemoAspectFactory getInstance() {
		return (TlDemoAspectFactory) com.top_logic.element.model.DynamicModelService.getFactoryFor(TL_DEMO_ASPECT_STRUCTURE);
	}
}
