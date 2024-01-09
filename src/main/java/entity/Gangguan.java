package entity;

import java.time.LocalDateTime;

import com.google.protobuf.UInt32Value;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "vw_gangguan_dialihkan_ke_posko_lain")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Gangguan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_gangguan;

    private long id_uid;

    private String no_laporan;

    private LocalDateTime waktu_dialihkan;

    private LocalDateTime waktu_response;

    private LocalDateTime waktu_recovery;

    @Nullable
    private long durasi_response_time;

    @Nullable
    private long durasi_recovery_time;

    private String posko_asal;

    private String posko_tujuan;

    private short status_akhir;

    private String idpel_nometer;

    private String nama_pelapor;

    private String alamat_pelapor;

    private String no_telp_pelapor;

    private String keterangan_pelapor;

    private String media;
}
