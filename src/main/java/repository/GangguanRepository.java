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
        Integer status, 
        LocalDate tglAwal, 
        LocalDate tglAkhir
    ) {
        StringBuilder query = new StringBuilder("1=1 ");
        Map<String,Object> parameter = new HashMap<>();
        
        if (status != null) {
            query.append("AND status = :status ");
            parameter.put("status", status);
        }

        if (tglAwal != null && tglAkhir != null) {
            query.append("AND date(tgl) between :tgl_awal and :tgl_akhir ");
            parameter.put("tgl_awal", tglAwal);
            parameter.put("tgl_akhir", tglAkhir);
    
        } else if (tglAwal != null) {
            query.append("AND date(tgl) >= :tgl_awal ");
            parameter.put("tgl_awal", tglAwal);
            
        } else if (tglAkhir != null) {
            query.append("AND date(tgl) <= :tgl_akhir ");
            parameter.put("tgl_akhir", tglAkhir);
        }
        
        return find(query.toString(),parameter).list();
    }

}
