import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import edu.princeton.cs.algs4.MinPQ;

public class BinaryTrie implements Serializable {
    private Node myTrie;
    private Map<Character, Integer> frequencyTable;
    //private MinPQ<Node> minpq = new MinPQ<>();
    private Map<Character, BitSequence> lookUpTable = new HashMap<>();

    // @source Princeton implementation given in the spec
    private class Node implements Comparable<Node> {
        private final char c;
        private final int freq;
        private final Node left, right;

        private Node(char c, int freq, Node left, Node right) {
            this.c = c;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        private boolean isLeaf() {
            return (left == null) && (right == null);
        }

        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    //Given a frequency table which maps symbols of type V to their relative
    // frequencies, the constructor should build a Huffman decoding
    // trie according to the procedure discussed in class
    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        this.frequencyTable = frequencyTable;
        this.myTrie = build();
    }

    //Match API
//    public class Match {
//        public Match(BitSequence sequence, char symbol)
//        public char getSymbol()
//        public BitSequence getSequence()
//    }

    //The longestPrefixMatch method finds the longest prefix that matches the
    //given querySequence and returns a Match object for that Match
    public Match longestPrefixMatch(BitSequence querySequence) {
        Node copy = myTrie;
        int length = querySequence.length();

        for (int i = 0; i < length; i++) {
            if (copy.isLeaf()) {
                Match match = new Match(querySequence.firstNBits(i), copy.c);
                return match;
            } else {
                if (querySequence.bitAt(i) == 0) {
                    copy = copy.left;
                } else {
                    copy = copy.right;
                }
            }
        }
        return new Match(querySequence, copy.c);
    }

    //@source help from the princeton implementation
    private void helperLookUp(Map<Character, BitSequence> lookupTable, Node n, String s) {
        if (!n.isLeaf()) {
            helperLookUp(lookupTable, n.left, s + '0');
            helperLookUp(lookupTable, n.right, s + '1');
        } else {
            lookupTable.put(n.c, new BitSequence(s));
        }
    }

    //The buildLookupTable method returns the inverse of the coding trie.
    public Map<Character, BitSequence> buildLookupTable() {
        helperLookUp(lookUpTable, myTrie, "");
        return lookUpTable;
    }

    //@source ideas adapted from princeton implementation give in spec
    private Node build() {
        MinPQ<Node> minpq = new MinPQ<>();
        for (char c: this.frequencyTable.keySet()) {
            minpq.insert(new Node(c, this.frequencyTable.get(c),
                    null, null));
        }
        while (minpq.size() > 1) {
            Node l = minpq.delMin();
            Node r = minpq.delMin();
            Node p = new Node('\0', l.freq + r.freq, l, r);
            minpq.insert(p);
        }
        return minpq.delMin();
    }

}