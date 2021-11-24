import java.util.*;
import java.io.*;
public class ConnectedComponents  {
    
    
    int[] findSet;
    ArrayList<MyLinkedList<Integer>> vertexSets;
   static int numVertex;
   static int numEdges;
    static ArrayList<WeightedEdge> edgeSet;
   static int totalChanges;
   int[] setArr;
   int[] height;
   int count;
    
    public ConnectedComponents(int n, int m){
        numVertex =n;
        numEdges=m;
        totalChanges=0;
        count=0;
       findSet = new int[n]; 
       setArr=new int[n];
       height=new int[n];
       vertexSets = new ArrayList<>(n);
       for (int i=0;i<n;i++) {
           findSet[i]=i;
           setArr[i]=i;
           
           vertexSets.add(new MyLinkedList<Integer>());
           vertexSets.get(i).addFirst(i);
        }
       // edgeSet = new ArrayList<>();
    }
    
    public int getComponentSize(int u){
       return vertexSets.get(u).size(); 
    }
    
    
    public int findComponent(int u) {
       return findSet[u]; 
    }

    public int findComponent2(int u) {
        int r = u;
        while (findSet[r] != r) {
            r = findSet[r];
            while (u != r) {
                int newU = findSet[u];
                findSet[u] = r;
                u = newU;
            }
        }
        return r;
    }

    
    public void changeComponent(MyLinkedList<Integer> my, int max){
        totalChanges+=my.size();
         if (!my.isEmpty()){
            Node<Integer> temp = my.getFirst();
            do {
            findSet[temp.getInfo()]=max;  
            temp = temp.getNext();  
        } while (temp!=null);
    }
        else {
               System.out.println("empty list ...");
            throw new NoSuchElementException();
        }
        
    }
    
    public static void readAndStoreGraph(String fileName, String fileName2) {
     
        try{
        Scanner sc =new Scanner(new File(fileName));
            Scanner sc2 =new Scanner(new File(fileName2));
        String s;
       int i =0;
       int  y;
       int maxV=0;
       edgeSet = new ArrayList<>();
      // graph.add(new ArrayList<Integer>());
       
     while (sc.hasNextLine()) {
           s = sc.nextLine();
           if (s.isEmpty()) continue;
           
          String[] line= s.split("\\s+");
          //String[] line= s.split(",");
        int v1=  Integer.parseInt(line[0]);

         y = sc2.nextInt();
        int v2=  Integer.parseInt(line[1]);
        int p= Math.max(v1,v2);
        if (p>maxV) maxV=p;
        edgeSet.add(new WeightedEdge(v1,v2,y));
        i++;
          
           
       
    }
    numEdges=i;
    numVertex=maxV;
}
catch (FileNotFoundException e) {
}
    
}

    
    public void getComponents(){
        HashSet<Integer> comp = new HashSet<>();
        ArrayList<Integer> al = new ArrayList<>();
        //int count=0;
        for (int i: findSet)
          comp.add(i);
        System.out.println(comp.size());
      /*  for (Integer j:comp) {
          al.add(vertexSets.get(j).size());
         // System.out.println();
        }
        Collections.sort(al);
        System.out.println(al);*/
        
    }
    
    public void mergeComponents (int u, int v) {
        int p = findComponent(u);
        int q = findComponent(v);
        int min=0;
        int max=0;
        if (p!=q) {
            if (getComponentSize(p)>getComponentSize(q)){
              min =q; 
              max=p;
            }
              else {
                    min=p;
                    max=q;
                }
            // merge min with the max
            MyLinkedList<Integer> myl = vertexSets.get(min);
            vertexSets.get(max).appendList(myl);
            changeComponent(myl,max);
            
        }
    }

    public void minimumSpanningTree()
    {
        PriorityQueue<WeightedEdge> pq = new PriorityQueue<>(this.edgeSet);
        double minWeight = 0;
        int edgeNum = 0;
        ArrayList<WeightedEdge> newEdeSet = new ArrayList<>();
        while(!pq.isEmpty() && edgeNum < 505145) {
            WeightedEdge next = pq.poll();
            int v1 = next.v1;
            int v2 = next.v2;
            if (this.findComponent(v1) != this.findComponent(v2)) {
                newEdeSet.add(next);
                minWeight += next.weight;
                mergeComponents(next.v1, next.v2);
                edgeNum++;
            }
        }
        System.out.println("Minumum Spanning Tree: "+ newEdeSet);
        System.out.println("Minimum Weight: "+ minWeight);
    }

    public void minimumSpanningTreeWithPathCompression()
    {
        int edgeNum = 0;
        PriorityQueue<WeightedEdge> pq = new PriorityQueue<>(this.edgeSet);
        double minWeight = 0;
        ArrayList<WeightedEdge> newEdeSet = new ArrayList<>();
        while(!pq.isEmpty() && edgeNum < 505145) {
            WeightedEdge next = pq.poll();
            if (this.findComponent2(next.v1) != this.findComponent2(next.v2)) {
                newEdeSet.add(next);
                minWeight += next.weight;
                mergeComponents(next.v1, next.v2);
                edgeNum++;
            }
        }
        System.out.println("Minumum Spanning Tree: "+ newEdeSet);
        System.out.println("Minimum Weight: "+ minWeight);
    }
       
     public static void main(String[] args) {
         
        //  numVertex=12;
        //  numEdges=10;
     //  readAndStoreGraph("Email-Enron.txt");
       //readAndStoreGraph("artist_edges.txt" , "Weights.txt" );
         ConnectedComponents cc = new ConnectedComponents(50515, 50514);
         readAndStoreGraph("artist_edges.txt" , "Weights.txt" );
         cc.minimumSpanningTree();
         ConnectedComponents gg = new ConnectedComponents(50515, 50514);
         readAndStoreGraph("artist_edges.txt" , "Weights.txt" );
         gg.minimumSpanningTreeWithPathCompression();
       //ConnectedComponents cc = new ConnectedComponents(numVertex+1,numEdges);

        
      /*  cc.edgeSet = new ArrayList<>();
         cc.edgeSet.add(new Edge(1,9));
         cc.edgeSet.add(new Edge(5,6));
         cc.edgeSet.add(new Edge(4,10));
         cc.edgeSet.add(new Edge(11,12));
         cc.edgeSet.add(new Edge(9,10));
         cc.edgeSet.add(new Edge(6,7));
         cc.edgeSet.add(new Edge(1,4));
         cc.edgeSet.add(new Edge(8,11));
         cc.edgeSet.add(new Edge(3,11));
         cc.edgeSet.add(new Edge(2,5));*/
         
         
         
    /*
     long t1 = System.currentTimeMillis();
         
         for (int i=0;i<numEdges;i++) {
           cc.mergeComponents  (cc.findComponent(edgeSet.get(i).v1),cc.findComponent(edgeSet.get(i).v2));
             
            }
             long t2=System.currentTimeMillis();
            System.out.println("time taken "+ (t2-t1)*1.0/1000);
            
           // cc.getComponents();
           // System.out.println(totalChanges);
       
         */
         
         
        }
        
    }
    
