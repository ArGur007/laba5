import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionManager{
    List<Experiment> experimentList = new ArrayList<>();
    private long nextId = 1L;

    private Map<Long, Experiment> experimentMap = new HashMap<>();

    public void create(Experiment exp) {
        if (exp.getId() == null){
            Experiment expWithId = new Experiment(
                    nextId++,
                    exp.getName(),
                    exp.getDescribtion(), exp.getOwnerUsername());
            experimentList.add(expWithId);
            System.out.println("OK. Experiment_id= "+expWithId.getId());
        } else {
            experimentList.add(exp);
            System.out.println("OK. Experiment_id= "+ exp.getId());
        }

    }

    public List<Experiment> getAll() {
        return new ArrayList<>(experimentList);
    }
    public Experiment findById(Long id){
        return experimentMap.get(id);
    }

}
