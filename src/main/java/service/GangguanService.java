package service;


import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import entity.Gangguan;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import repository.GangguanRepository;

@ApplicationScoped
public class GangguanService {
    @Inject
    GangguanRepository gangguanRepository;

    public Uni<List<Gangguan>> getAllGangguan() {
        return gangguanRepository
                .listAll(Sort.by("tgl"))
                .ifNoItem()
                .after(Duration.ofMillis(10000))
                .fail()
                .onFailure()
                .recoverWithUni(throwable -> Uni.createFrom().item(Collections.emptyList()));
    }

    public Uni<List<Gangguan>> getGangguanByCustomFilter(
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

        return gangguanRepository.customSearch(query.toString(),parameter);
    }
}
