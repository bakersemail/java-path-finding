package com.bakersoftware.game.common.ai;

import java.util.List;

public interface TreeNode extends Comparable<TreeNode> {
	List<TreeNode> getAdjacentNodes();

	void addAjacentNode(TreeNode treeNode);

	int getWeight();

	void setWeight(int weight);

	TreeNode getParent();

	void setParent(TreeNode parent);

	int getIndex();

	void detach();
}
