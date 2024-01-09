package repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import entity.Gangguan;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GangguanRepository implements PanacheRepositoryBase<Gangguan, Long> {

    public List<Gangguan> allGangguanWithFilter(
            LocalDate dateFrom,
            LocalDate dateTo,
            String posko,
            int id_uid,
            int id_up3) {
        StringBuilder query = new StringBuilder("1=1 ");
        Map<String, Object> parameter = new HashMap<>();

        query.append("(date_from = :dateFrom,date_to = :dateTo, posko = :posko, id_uid = :id_uid, id_up3 = :id_up3)");
        parameter.put("dateFrom", dateFrom);
        parameter.put("dateTo", dateTo);
        parameter.put("posko", posko);
        parameter.put("id_uid", id_uid);
        parameter.put("id_up3", id_up3);

        return find(query.toString(), parameter).list();
    }

}
