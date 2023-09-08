/*
 * SPDX-FileCopyrightText: 2022 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.kafka.demo.model.types;

/**
 * Factory for <code>kafka.demo</code> objects.
 * 
 * <p>
 * Note: this is generated code. Do not modify. Instead, create a subclass and register this in the module system.
 * </p>
 * 
 * @author Automatically generated by {@link com.top_logic.element.model.generate.FactoryGenerator}
 */
public class KafkaDemoFactory extends com.top_logic.element.meta.kbbased.AbstractElementFactory {

	/**
	 * Name of the structure <code>kafka.demo</code> defined by {@link KafkaDemoFactory}.
	 */
	public static final String KAFKA_DEMO_STRUCTURE = "kafka.demo";

	/**
	 * Lookup {@link CommonNode} type.
	 */
	public static com.top_logic.model.TLClass getCommonNodeType() {
		return (com.top_logic.model.TLClass) com.top_logic.util.model.ModelService.getApplicationModel().getModule(KAFKA_DEMO_STRUCTURE).getType(CommonNode.COMMON_NODE_TYPE);
	}

	/**
	 * Lookup {@link CommonNode#CLASSIFICATION_MULTI_ATTR} of {@link CommonNode}.
	 */
	public static com.top_logic.model.TLReference getClassificationMultiCommonNodeAttr() {
		return (com.top_logic.model.TLReference) getCommonNodeType().getPart(CommonNode.CLASSIFICATION_MULTI_ATTR);
	}

	/**
	 * Lookup {@link CommonNode#NAME_ATTR} of {@link CommonNode}.
	 */
	public static com.top_logic.model.TLProperty getNameCommonNodeAttr() {
		return (com.top_logic.model.TLProperty) getCommonNodeType().getPart(CommonNode.NAME_ATTR);
	}

	/**
	 * Lookup {@link CommonNode#NOT_EXTERNALIZED_ATTR} of {@link CommonNode}.
	 */
	public static com.top_logic.model.TLProperty getNotExternalizedCommonNodeAttr() {
		return (com.top_logic.model.TLProperty) getCommonNodeType().getPart(CommonNode.NOT_EXTERNALIZED_ATTR);
	}

	/**
	 * Lookup {@link Node} type.
	 */
	public static com.top_logic.model.TLClass getNodeType() {
		return (com.top_logic.model.TLClass) com.top_logic.util.model.ModelService.getApplicationModel().getModule(KAFKA_DEMO_STRUCTURE).getType(Node.NODE_TYPE);
	}

	/**
	 * Lookup {@link Node#NOT_EXTERNALIZED_ATTR} of {@link Node}.
	 */
	public static com.top_logic.model.TLProperty getNotExternalizedNodeAttr() {
		return (com.top_logic.model.TLProperty) getNodeType().getPart(Node.NOT_EXTERNALIZED_ATTR);
	}

	/**
	 * Lookup {@link Node#OTHER_NODE_ATTR} of {@link Node}.
	 */
	public static com.top_logic.model.TLReference getOtherNodeNodeAttr() {
		return (com.top_logic.model.TLReference) getNodeType().getPart(Node.OTHER_NODE_ATTR);
	}

	/**
	 * Lookup {@link Node#UNTRANSFERRED_NODE1_ATTR} of {@link Node}.
	 */
	public static com.top_logic.model.TLReference getUntransferredNode1NodeAttr() {
		return (com.top_logic.model.TLReference) getNodeType().getPart(Node.UNTRANSFERRED_NODE1_ATTR);
	}

	/**
	 * Lookup {@link Node#UNTRANSFERRED_NODE2_ATTR} of {@link Node}.
	 */
	public static com.top_logic.model.TLReference getUntransferredNode2NodeAttr() {
		return (com.top_logic.model.TLReference) getNodeType().getPart(Node.UNTRANSFERRED_NODE2_ATTR);
	}

	/**
	 * Lookup {@link RemoteNode} type.
	 */
	public static com.top_logic.model.TLClass getRemoteNodeType() {
		return (com.top_logic.model.TLClass) com.top_logic.util.model.ModelService.getApplicationModel().getModule(KAFKA_DEMO_STRUCTURE).getType(RemoteNode.REMOTE_NODE_TYPE);
	}

	/**
	 * Lookup {@link RemoteNode#EXTERNAL_ID_ATTR} of {@link RemoteNode}.
	 */
	public static com.top_logic.model.TLProperty getExternalIdRemoteNodeAttr() {
		return (com.top_logic.model.TLProperty) getRemoteNodeType().getPart(RemoteNode.EXTERNAL_ID_ATTR);
	}

	/**
	 * Lookup {@link RemoteNode#OTHER_REMOTE_NODE_ATTR} of {@link RemoteNode}.
	 */
	public static com.top_logic.model.TLReference getOtherRemoteNodeRemoteNodeAttr() {
		return (com.top_logic.model.TLReference) getRemoteNodeType().getPart(RemoteNode.OTHER_REMOTE_NODE_ATTR);
	}

	/**
	 * Lookup {@link UntransferredNode} type.
	 */
	public static com.top_logic.model.TLClass getUntransferredNodeType() {
		return (com.top_logic.model.TLClass) com.top_logic.util.model.ModelService.getApplicationModel().getModule(KAFKA_DEMO_STRUCTURE).getType(UntransferredNode.UNTRANSFERRED_NODE_TYPE);
	}

	/**
	 * Name of type <code>CommonNode</code> in structure {@link #KAFKA_DEMO_STRUCTURE}.
	 * 
	 * @deprecated Use {@link CommonNode#COMMON_NODE_TYPE}.
	 */
	@Deprecated
	public static final String COMMON_NODE_NODE = CommonNode.COMMON_NODE_TYPE;

	/**
	 * Name of type <code>Node</code> in structure {@link #KAFKA_DEMO_STRUCTURE}.
	 * 
	 * @deprecated Use {@link Node#NODE_TYPE}.
	 */
	@Deprecated
	public static final String NODE_NODE = Node.NODE_TYPE;

	/**
	 * Storage table name of {@link #NODE_NODE} objects.
	 */
	public static final String KO_NAME_NODE = "KafkaDemo";

	/**
	 * Name of type <code>RemoteNode</code> in structure {@link #KAFKA_DEMO_STRUCTURE}.
	 * 
	 * @deprecated Use {@link RemoteNode#REMOTE_NODE_TYPE}.
	 */
	@Deprecated
	public static final String REMOTE_NODE_NODE = RemoteNode.REMOTE_NODE_TYPE;

	/**
	 * Storage table name of {@link #REMOTE_NODE_NODE} objects.
	 */
	public static final String KO_NAME_REMOTE_NODE = "KafkaDemoRemote";

	/**
	 * Name of type <code>UntransferredNode</code> in structure {@link #KAFKA_DEMO_STRUCTURE}.
	 * 
	 * @deprecated Use {@link UntransferredNode#UNTRANSFERRED_NODE_TYPE}.
	 */
	@Deprecated
	public static final String UNTRANSFERRED_NODE_NODE = UntransferredNode.UNTRANSFERRED_NODE_TYPE;

	/**
	 * Storage table name of {@link #UNTRANSFERRED_NODE_NODE} objects.
	 */
	public static final String KO_NAME_UNTRANSFERRED_NODE = "KafkaDemoUntransferred";


	/**
	 * Create an instance of {@link Node} type.
	 */
	public final Node createNode(com.top_logic.model.TLObject context) {
		return (Node) createObject(getNodeType(), context);
	}

	/**
	 * Create an instance of {@link Node} type.
	 */
	public final Node createNode() {
		return createNode(null);
	}

	/**
	 * Create an instance of {@link RemoteNode} type.
	 */
	public final RemoteNode createRemoteNode(com.top_logic.model.TLObject context) {
		return (RemoteNode) createObject(getRemoteNodeType(), context);
	}

	/**
	 * Create an instance of {@link RemoteNode} type.
	 */
	public final RemoteNode createRemoteNode() {
		return createRemoteNode(null);
	}

	/**
	 * Create an instance of {@link UntransferredNode} type.
	 */
	public final UntransferredNode createUntransferredNode(com.top_logic.model.TLObject context) {
		return (UntransferredNode) createObject(getUntransferredNodeType(), context);
	}

	/**
	 * Create an instance of {@link UntransferredNode} type.
	 */
	public final UntransferredNode createUntransferredNode() {
		return createUntransferredNode(null);
	}

	/**
	 * The singleton instance of {@link KafkaDemoFactory}.
	 */
	public static KafkaDemoFactory getInstance() {
		return (KafkaDemoFactory) com.top_logic.element.model.DynamicModelService.getFactoryFor(KAFKA_DEMO_STRUCTURE);
	}
}
