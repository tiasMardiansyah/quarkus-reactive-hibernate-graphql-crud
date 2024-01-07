package boundary;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import javax.inject.Inject;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import entity.Gangguan;
import io.smallrye.mutiny.Uni;
import io.vertx.core.cli.annotations.Description;
import service.GangguanService;

@GraphQLApi
public class GangguanResource {
    @Inject
    GangguanService gangguanService;

    // @Query("allGangguan")
    // @Description("Get all Gangguan")
    // public Uni<List<Gangguan>> getAllGangguan(@Name("status") Optional<Integer>
    // status,
    // @Name("tgl_awal") Optional<String> tgl_awal, @Name("tgl_akhir")
    // Optional<String> tgl_akhir) {
    // return gangguanService.getAllGangguan(status.get(), tgl_awal.get(),
    // tgl_akhir.get());
    // }

    @Query("getGangguanByCustomFilter")
    @Description("Get all Gangguan using custom filter, Experimental ")
    public Uni<List<Gangguan>> getGangguanByCustomFilter(
        @Name("status") Optional<Integer> status,
        @Name("tgl_awal") Optional<LocalDate> tglAwal,
        @Name("tgl_akhir") Optional<LocalDate> tglAkhir
    ) {
        return gangguanService.getGangguanByCustomFilter(status.orElse(null), tglAwal.orElse(null), tglAkhir.orElse(null));
    }

}
