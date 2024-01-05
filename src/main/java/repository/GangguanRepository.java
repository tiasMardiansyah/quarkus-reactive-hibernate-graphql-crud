package repository;

import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import entity.Gangguan;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class GangguanRepository implements PanacheRepositoryBase<Gangguan, Long> {

    public Uni<List<Gangguan>> findByStatus(int status) {
        Map<String, Object> filter = Map.of(
                "status", status);

        StringBuilder query = new StringBuilder();

        if (true) {
            query.append("and status = :status");
        }

        return find(query.toString(), filter).list();

    }
}
