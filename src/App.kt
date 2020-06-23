import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.max
import kotlin.math.min

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
class Codec {

    private val DELIMITER = "-"

    // Encodes a URL to a shortened URL.
    fun serialize(root: TreeNode?): String {
        var treeString = ""
        if (root == null) {
            return "*-"
        }
        treeString += "${root.`val`}$DELIMITER"
        treeString += serialize(root.left)
        treeString += serialize(root.right)
        return treeString
    }

    // Decodes your encoded data to tree.
    fun deserialize(data: String): TreeNode? {
        if (data == "") {
            return null
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
        val root = TreeNode(res[0].toInt())
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

    // 1028. 从先序遍历还原二叉树
    fun recoverFromPreorder(S: String): TreeNode? {
        if (S == "") {
            return null
        }
        val arr = ArrayList<Int>()
        arr.add(0)
        for (i in 1 until S.length) {
            if (S[i] != '-' && S[i - 1] == '-') {
                // num start
                arr.add(i)
            } else if (S[i] == '-' && S[i-1] != '-') {
                // num end
                arr.add(i - 1)
            }
        }
        arr.add(S.length - 1)
        var start = 0
        var lastEnd = -1
        val nums = IntArray(arr.size)
        nums[0] = 0
        var k = 0
        for (end in 1 until arr.size step 2) {
            start = arr[end - 1]
            nums[k++] = start - lastEnd - 1
            if (start == arr[end]) {
                nums[k++] = S[arr[end]] - '0'
            } else {
                nums[k++] = S.substring(start, arr[end] + 1).toInt()
            }
            lastEnd = arr[end]
        }
        val root = TreeNode(nums[1])
        var node = root
        var lastDeep = 0
        var parent = root
        val stack = LinkedList<TreeNode>()
        stack.push(root)
        for (i in 2 until nums.size step 2) {
            when {
                nums[i] < lastDeep -> {
                    for (j in 0 until lastDeep - nums[i] + 1) {
                        stack.pop()
                    }
                    parent = stack.peek()
                    parent.right = TreeNode(nums[i + 1])
                    stack.push(parent.right)
                }
                nums[i] == lastDeep -> {
                    stack.pop()
                    parent = stack.peek()
                    parent.right = TreeNode(nums[i + 1])
                    stack.push(parent.right)
                }
                else -> {
                    parent = stack.peek()
                    parent.left = TreeNode(nums[i + 1])
                    stack.push(parent.left)
                }
            }
            lastDeep = nums[i]
        }
        return root
    }

    // 125. 验证回文串
    fun isPalindrome(s: String): Boolean {
        var p = 0
        var q = s.length - 1

        while (p < q) {
            if (!s[p].isLetterOrDigit()) {
                p++
                continue
            }
            if (!s[q].isLetterOrDigit()) {
                q--
                continue
            }
            if (s[p++].toLowerCase() != s[q--].toLowerCase()) {
                return false
            }
        }
        return true
    }

    // 299. 猜数字游戏
    fun getHint(secret: String, guess: String): String {
        val nums = IntArray(10)
        val wrongs = IntArray(10)
        var bulls = 0
        var cows = 0
        for (i in guess.indices) {
            if (secret[i] == guess[i]) {
                bulls++
            } else {
                nums[secret[i] - '0']++
                wrongs[guess[i] - '0']++
            }
        }
        for (i in nums.indices) {
            cows += min(nums[i], wrongs[i])
        }
        return "${bulls}A${cows}B"
    }

    var pathMax = Int.MIN_VALUE

    fun maxPathSum(root: TreeNode?): Int {
        if (root == null) {
            return 0
        }
        findMax(root)
        return pathMax
    }

    fun findMax(root: TreeNode?): Int {
        return if (root == null) {
            0
        } else {
            val left = max(0, findMax(root.left))
            val right = max(0, findMax(root.right))
            pathMax = max(pathMax, left + right + root.`val`)
            max(left, right) + root.`val`
        }
    }

    fun addBinary(a: String, b: String): String {
        if (a == "") {
            return b
        } else if (b == "") {
            return a
        }
        val mLen = max(a.length, b.length)
        val res = IntArray(mLen + 1)
        var i = 0
        var c = 0
        var na: Int;
        var nb: Int;
        while (i < a.length && i < b.length) {
            na = a[a.length - i - 1] - '0'
            nb = b[b.length - i - 1] - '0'
            res[mLen - i] = na + nb + c
            c = 0
            if (res[mLen - i] == 2) {
                c = 1
                res[mLen - i] = 0
            } else if (res[mLen - i] == 3) {
                c = 1
                res[mLen - i] = 1
            }
            i++
        }
        while (i < a.length) {
            res[mLen - i] = a[a.length - i - 1] - '0' + c
            c = 0
            if (res[mLen - i] > 1) {
                c = 1
                res[mLen - i] = 0
            }
            i++
        }
        while (i < b.length) {
            res[mLen - i] = b[b.length - i - 1] - '0' + c
            c = 0
            if (res[mLen - i] > 1) {
                c = 1
                res[mLen - i] = 0
            }
            i++
        }
        var start = 0
        if (c > 0) {
            res[0] = c
        } else {
            start = 1
        }
        var ans = ""
        for (k in start until mLen + 1) {
            ans += res[k]
        }
        return ans
    }
}

fun main() {
    val solution = Solution()
    println(solution.addBinary("110", "110010"))
}