import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 *
 * @date 18:36 2020/6/16
 * @author LiuHaonan
 * @email acerola.orion@foxmail.com
 */

/**
 * 297. 二叉树的序列化与反序列化
 * https://leetcode-cn.com/problems/serialize-and-deserialize-binary-tree/
 */
fun main() {
    val obj = Codec();

    val root = TreeNode(1)
    root.left = TreeNode(2)
    root.left?.left = TreeNode(3)
    root.right = TreeNode(4)
    root.right?.right = TreeNode(5)

    val serialize = obj.serialize(root)
    println(serialize)

    val newRoot = obj.deserialize(serialize)
    println(obj.serialize(newRoot))
}

class Codec() {

    private val DELIMITER = "-";

    // Encodes a URL to a shortened URL.
    fun serialize(root: TreeNode?): String {
        var treeString = "";
        if (root == null) {
            return "*-";
        }
        treeString += "${root.`val`}$DELIMITER"
        treeString += serialize(root.left)
        treeString += serialize(root.right)
        return treeString
    }

    // Decodes your encoded data to tree.
    fun deserialize(data: String): TreeNode? {
        if (data == "") {
            return null;
        }
        val res = ArrayList(data.split("-"))
        res.removeAt(res.size - 1)
        return makeTree(res)
    }

    private fun makeTree(res: ArrayList<String>): TreeNode? {
        if (res.isEmpty() || res[0] == "*") {
            res.removeAt(0)
            return null
        }
        val root = TreeNode(res[0].toInt());
        res.removeAt(0)
        root.left = makeTree(res)
        root.right = makeTree(res)
        return root
    }
}

class Solution {

    // 1014. 最佳观光组合
    fun maxScoreSightseeingPair(A: IntArray): Int {
        var left = A[0]
        var max = Int.MIN_VALUE
        for (i in 1 until A.size) {
            max = max.coerceAtLeast(left + A[i] - i)
            left = left.coerceAtLeast(A[i] + i)
        }
        return max
    }
}