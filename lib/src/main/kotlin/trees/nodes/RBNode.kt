package trees.nodes

class RBNode<K : Comparable<K>, V>(key: K, value: V) : Node<K, V, RBNode<K, V>>(key, value) {
    internal var parent: RBNode<K, V>? = null
    internal var color: Color = Color.BLACK

    internal enum class Color {
        BLACK,
        RED
    }
}
