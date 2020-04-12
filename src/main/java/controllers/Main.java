package controllers;

import com.github.javafaker.Faker;
import dao.ChartDaoImp;
import database.Database;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Random;


import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class Main {
    public static void main(String[] args) throws SQLException, Exception {
        Database db = Database.getConnection();
        ChartController chart = new ChartController();
        Main main = new Main();
        //main.generateData(db);


        /*chart.findByCriteria("artist", null, db);
        chart.findByCriteria("album", null, db);
        chart.findByCriteria("country", "Romania", db);
        chart.findByCriteria("country", "USA", db);
        chart.findByCriteria("country", "UK", db);*/


        /* !!! GENERARE HTML !!! */
        ChartDaoImp chart2 = new ChartDaoImp();

        Configuration cfg = new Configuration();

        cfg.setClassForTemplateLoading(Main.class, "/");
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Map<String, Object> input = new HashMap<String, Object>();
        input.put("title", "CLASAMENT");
        input.put("clasament", chart2.findByCriteria("artist", null, db));

        Template template = cfg.getTemplate("clasament.ftl");
        Writer fileWriter = new FileWriter(new File("clasament.html"));
        try {
            template.process(input, fileWriter);
        } finally {
            fileWriter.close();
        }
        /* !!! GENERARE HTML !!!   */

    }

    /**
     * Functie ce va genera aleatoriu date in baza de date
     * Indroduc 30 de artisti, 30 de albume si 90 de albume in chart-uri (fiecare album va fi in toate cele 3 clasamente
     *  dupa artist, dupa album si dupa tara)
     * Folosesc array de frecventa pentru a marca unicitatea rank-ului pentru fiecare criteriu
     * De mentionat este ca vom crea 3 chart-uri pentru tari, fiecare char al fiecarei tari va avea 10 albume.
     *  Aceasta am realizat-o printr-un calcul simplu: id % 3 = 0 => Romania, id % 3 = 1 => USA, id % 3 = 2 => UK
     * Inserarea in tabelul charts nu se face decat dupa ce se gaseste un int (rank) care nu a mai fost folosit niciodata (rank unic)
     *  @param db
     * @throws SQLException
     */
    public void generateData(Database db) throws SQLException {
        ArtistController artist = new ArtistController();
        AlbumController album = new AlbumController();
        ChartController chart = new ChartController();
        Faker faker = new Faker();
        Random random = new Random();
        int place;
        int[] frArtist = new int[31], frAlbum = new int[31], frRomania = new int[31], frUSA = new int[31], frUK = new int[31];
        Arrays.fill(frArtist, 0);
        Arrays.fill(frAlbum, 0);
        Arrays.fill(frRomania, 0);
        Arrays.fill(frUSA, 0);
        Arrays.fill(frUK, 0);

        for(int i = 1; i <= 30; i++) {
            artist.create(faker.gameOfThrones().character(), i % 3 == 0 ? "Romania" : (i % 3 == 1 ? "USA" : "UK"), db);

            album.create(faker.book().title(), i, 2000 + random.nextInt(20), db);

            while((frArtist[place = random.nextInt(30) + 1]) != 0) ;
            frArtist[place] = 1;
            chart.create(i, "artist", place, db);

            while((frAlbum[place = random.nextInt(30) + 1]) != 0) ;
            frAlbum[place] = 1;
            chart.create(i, "album", place, db);

            if(i % 3 == 0) {
                while((frRomania[place = random.nextInt(10) + 1]) != 0) ;
                frRomania[place] = 1;
                chart.create(i, "country", place, db);
            }
            else if(i % 3 == 1) {
                while((frUSA[place = random.nextInt(10) + 1]) != 0) ;
                frUSA[place] = 1;
                chart.create(i, "country", place, db);
            }
            else {
                while((frUK[place = random.nextInt(10) + 1]) != 0) ;
                frUK[place] = 1;
                chart.create(i, "country", place, db);
            }
        }
    }
}
