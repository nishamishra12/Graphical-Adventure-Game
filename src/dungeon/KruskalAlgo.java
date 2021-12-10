package dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Citation for the code:
 * https://www.techiedelight.com/kruskals-algorithm-for-finding-minimum-spanning-tree/
 * This class represents the Kruskal Algorithm which is used to build the dungeon.
 */
class KruskalAlgo {

  private Map<Integer, Integer> parent;

  private void makeSet(int n) {
    parent = new HashMap<Integer, Integer>();
    for (int i = 0; i < n; i++) {
      parent.put(i, i);
    }
  }

  private int findRootOfSet(int k) {
    if (parent.get(k) == k) {
      return k;
    }
    return findRootOfSet(parent.get(k));
  }

  private void union(int a, int b) {
    int x = findRootOfSet(a);
    int y = findRootOfSet(b);
    parent.put(x, y);
  }

  public List<Edge> kruskalAlgo(List<Edge> edges, int n) {
    List<Edge> mst = new ArrayList<Edge>();
    KruskalAlgo ds = new KruskalAlgo();
    ds.makeSet(n);

    int index = 0;
    Collections.sort(edges, Comparator.comparingInt(e -> e.getWeight()));

    while (true) {
      if (mst.size() == n - 1) break;
      Edge nextEdge = edges.get(index++);
      int source = ds.findRootOfSet(nextEdge.getSrc());
      int destination = ds.findRootOfSet(nextEdge.getDest());

      if (source != destination) {
        mst.add(nextEdge);
        ds.union(source, destination);
      }
    }
    return new ArrayList<Edge>(mst);
  }
}
