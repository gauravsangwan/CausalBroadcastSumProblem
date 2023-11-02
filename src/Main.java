import io.jbotsim.core.Node;
import io.jbotsim.core.Topology;
import io.jbotsim.ui.JViewer;
import io.jbotsim.core.Node;
import static io.jbotsim.contrib.algos.Connectivity.createTopology;
import static io.jbotsim.gen.basic.TopologyLayouts.center;
public class Main{
    public static void main(String[] args){
        Topology tp = new Topology();
//        tp = createTopology(50,50,1,100);
//        center(tp);
        tp.setDefaultNodeModel(Counter.class);
        tp.setTimeUnit(500);
        new JViewer(tp);
        tp.start();
    }
}