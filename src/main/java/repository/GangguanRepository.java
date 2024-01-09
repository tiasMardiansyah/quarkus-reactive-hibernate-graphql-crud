package repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import entity.Gangguan;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class GangguanRepository implements PanacheRepositoryBase<Gangguan, Long> {

    @Inject
    Session session;

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

    
    
    //mapping secara manual
    public List<Gangguan> allGangguanWithFilterCustomQueryManual(
        Integer status, 
        LocalDate tglAwal, 
        LocalDate tglAkhir
    ) {
        StringBuilder query = new StringBuilder("select * from dat_gangguan");
        Map<String,Object> parameter = new HashMap<>();
        try {

            query.append(" WHERE 1=1 ");
        
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
           
            Query<Object[]> hibernateQuery = session.createNativeQuery(query.toString(), Object[].class);

            // Set parameters
            for (Map.Entry<String, Object> entry : parameter.entrySet()) {
                hibernateQuery.setParameter(entry.getKey(), entry.getValue());
            }

            List<Object[]> resultList = hibernateQuery.list();

            List<Gangguan> gangguanList = new ArrayList<>();
            resultList.forEach(i -> {
                gangguanList.add(new Gangguan(
                    (int)i[0],
                    (String)i[1],
                    (Date)i[2],
                    (int)i[3]
                ));
            });

            return gangguanList;

        } catch(HibernateException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    //mapping hasil otomatis berdasarkan kelas
    public List<Gangguan> allGangguanWithFilterCustomQueryMapped(
        Integer status, 
        LocalDate tglAwal, 
        LocalDate tglAkhir
    ) {
        StringBuilder query = new StringBuilder("select * from dat_gangguan");
        Map<String,Object> parameter = new HashMap<>();
        try {

            query.append(" WHERE 1=1 ");
        
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
       
            Query<Gangguan> hibernateQuery = session.createNativeQuery(query.toString(), Gangguan.class);

            for (Map.Entry<String, Object> entry : parameter.entrySet()) {
                hibernateQuery.setParameter(entry.getKey(), entry.getValue());
            }

            return hibernateQuery.list();

        } catch(HibernateException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
