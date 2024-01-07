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

        // lakukan pengolahan data disini

        return gangguanRepository.allGangguanWithFilter(status, tglAwal, tglAkhir)
                .ifNoItem()
                .after(Duration.ofMillis(10000))
                .fail()
                .onFailure()
                .recoverWithUni(t -> Uni.createFrom().item(Collections.emptyList()));
    }
}
