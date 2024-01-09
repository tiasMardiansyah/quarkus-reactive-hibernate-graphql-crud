package service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import entity.Gangguan;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import repository.GangguanRepository;

@ApplicationScoped
public class GangguanService {
    @Inject
    GangguanRepository gangguanRepository;

    public Uni<List<Gangguan>> getAllGangguan() {
        return Uni.createFrom().item(() -> (gangguanRepository.listAll(Sort.by("tgl"))))
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .ifNoItem()
                .after(Duration.ofMillis(10000))
                .fail()
                .onFailure()
                .recoverWithUni(throwable -> Uni.createFrom().item(Collections.emptyList()));
    }

    public Uni<List<Gangguan>> getGangguanWithFilter(LocalDate dateFrom, LocalDate dateTo, String posko, int id_uid,
            int id_up3) {
        return Uni.createFrom()
                .item(() -> gangguanRepository.allGangguanWithFilter(
                        dateFrom,
                        dateTo,
                        posko,
                        id_uid,
                        id_up3))
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .ifNoItem()
                .after(Duration.ofMillis(10000))
                .fail()
                .onFailure()
                .recoverWithUni(throwable -> Uni.createFrom().item(Collections.emptyList()));
    }
}
