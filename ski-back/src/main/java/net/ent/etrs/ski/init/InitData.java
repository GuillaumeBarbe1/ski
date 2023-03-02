package net.ent.etrs.ski.init;

import com.github.javafaker.Faker;
import net.ent.etrs.ski.exceptions.DaoException;
import net.ent.etrs.ski.model.daos.DaoForfait;
import net.ent.etrs.ski.model.daos.DaoPiste;
import net.ent.etrs.ski.model.daos.DaoRemontee;
import net.ent.etrs.ski.model.daos.DaoStation;
import net.ent.etrs.ski.model.entities.Forfait;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.entities.Remontee;
import net.ent.etrs.ski.model.entities.Station;
import net.ent.etrs.ski.model.entities.references.Etat;
import net.ent.etrs.ski.model.entities.references.Niveau;


import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Startup
@Singleton
public class InitData {
    
    private Faker faker = Faker.instance(Locale.FRANCE);
    
    @Inject
    private DaoPiste daoPiste;

    @Inject
    private DaoStation daoStation;

    @Inject
    private DaoRemontee daoRemontee;

    @Inject
    private DaoForfait daoForfait;


    @PostConstruct
    public void init() {
        try {
            this.initRemontees();
            this.initForfait();
            this.initPistes();
            this.initStations();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initStations() throws DaoException {
        if (this.daoStation.count() == 0) {
            Set<Station> stations = new HashSet<>();
            do {
                Station s = new Station();
                s.setNom(this.faker.dragonBall().character()+" "+this.faker.name().suffix());
                s.setVille(this.faker.country().name()+" "+this.faker.name().suffix());
                s.setNbHabitants(this.faker.random().nextInt(1, 10000));
                s.setAltitude(this.faker.random().nextInt(1, 1500));
                s.setEtat(Etat.values()[this.faker.random().nextInt(0, Etat.values().length-1)]);
                stations.add(s);
            } while (stations.size() <= 20);

            for (Station s : stations) {
                this.daoStation.save(s);
            }
        }
    }


    private void initForfait() throws DaoException {
        if (this.daoForfait.count() == 0) {
            Set<Forfait> forfaits = new HashSet<>();
            do {
                Forfait f = new Forfait();
                f.setNom(this.faker.aviation().aircraft()+" "+this.faker.name().suffix());
                f.setPrixJournalier(BigDecimal.valueOf(this.faker.random().nextInt(1, 200)));
                forfaits.add(f);
            } while (forfaits.size() <= 50);

            for (Forfait f : forfaits) {
                this.daoForfait.save(f);
            }
        }
    }

    private void initRemontees() throws DaoException {
        if (this.daoRemontee.count() == 0) {
            Set<Remontee> remontees = new HashSet<>();
            do {
                Remontee r = new Remontee();
                r.setEtat(Etat.values()[this.faker.random().nextInt(0, Etat.values().length-1)]);
                r.setNom(this.faker.harryPotter().character()+" "+this.faker.name().suffix());
                r.setNbPlaceUnite(this.faker.random().nextInt(1, 15));
                remontees.add(r);
            } while (remontees.size() <= 50);

            for (Remontee r : remontees) {
                this.daoRemontee.save(r);
            }
        }
    }

    private void initPistes() throws Exception {
        if (this.daoPiste.count() == 0) {
            Set<Piste> pistes = new HashSet<>();
            do {
                Piste p = new Piste();
                p.setEtat(Etat.values()[this.faker.random().nextInt(0, Etat.values().length-1)]);
                p.setNom(this.faker.name().name()+" "+this.faker.name().suffix());
                p.setLongueur(this.faker.random().nextInt(1000, 20000));
                p.setPenteMoy(this.faker.number().randomDouble(2,1,15));
                p.setNiveau(Niveau.values()[this.faker.random().nextInt(0, Niveau.values().length-1)]);
                pistes.add(p);
            } while (pistes.size() <= 50);
    
            for (Piste p : pistes) {
                this.daoPiste.save(p);
            }
        }
    }
}
