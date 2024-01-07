package repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import entity.Gangguan;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class GangguanRepository implements PanacheRepositoryBase<Gangguan, Long> {

    public Uni<List<Gangguan>> allGangguanWithFilter(
        Optional<Integer> status, 
        Optional<LocalDate> tglAwal, 
        Optional<LocalDate> tglAkhir
    ) {
        StringBuilder query = new StringBuilder("1=1 ");
        Map<String,Object> parameter = new HashMap<>();
        
        if (!status.isEmpty()) {
            query.append("AND status = :status ");
            parameter.put("status", status.get());
        }

        if (!tglAwal.isEmpty() && !tglAkhir.isEmpty()) {
            query.append("AND date(tgl) between :tgl_awal and :tgl_akhir ");
            parameter.put("tgl_awal", tglAwal.get());
            parameter.put("tgl_akhir", tglAkhir.get());
    
        } else if (!tglAwal.isEmpty()) {
            query.append("AND date(tgl) >= :tgl_awal ");
            parameter.put("tgl_awal", tglAwal.get());
            
        } else if (!tglAkhir.isEmpty()) {
            query.append("AND date(tgl) <= :tgl_akhir ");
            parameter.put("tgl_akhir", tglAkhir.get());
        }
        
        return find(query.toString(), parameter).list();
    }

}
