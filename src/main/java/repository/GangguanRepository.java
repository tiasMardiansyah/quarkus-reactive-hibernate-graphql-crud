package repository;

import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import entity.Gangguan;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class GangguanRepository implements PanacheRepositoryBase<Gangguan, Long> {

    public Uni<List<Gangguan>> customSearch(String query, Map<String,Object> parameter) {
        return find(query.toString(), parameter).list();
    }

}
