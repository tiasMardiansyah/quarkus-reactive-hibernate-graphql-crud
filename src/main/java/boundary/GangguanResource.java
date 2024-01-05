package boundary;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import entity.Gangguan;
import io.smallrye.mutiny.Uni;
import io.vertx.core.cli.annotations.Description;
import lombok.extern.java.Log;
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

    @Query("gangguanByStatus")
    @Description("Get all Gangguan")
    public Uni<List<Gangguan>> gangguanByStatus(@Name("status") Optional<Integer> status,
            @Name("tgl_awal") Optional<String> tgl_awal, @Name("tgl_akhir") Optional<String> tgl_akhir) {
        return gangguanService.gangguanByStatus(1);
    }

}
