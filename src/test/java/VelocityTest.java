
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import static org.assertj.core.api.Assertions.*;

import java.io.*;

public class VelocityTest {

    public VelocityTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void hello() throws Exception {
        VelocityEngine velocityEngine = new VelocityEngine();

        velocityEngine.init();

        Template t = velocityEngine.getTemplate("src/test/resources/template.vm");

        VelocityContext context = new VelocityContext();

        ZoneId fusoHorarioDeSaoPaulo = ZoneId.of("America/Sao_Paulo");
        ZonedDateTime horaSaoPaulo = ZonedDateTime.of(LocalDateTime.now(), fusoHorarioDeSaoPaulo);

        context.put("dataGeracao", horaSaoPaulo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

        StringWriter writer = new StringWriter();
        t.merge(context, writer);

        Document output = new Document(PageSize.LETTER, 40, 40, 40, 40);

        PdfWriter.getInstance(output, new FileOutputStream("./target/template.pdf"));
        Paragraph p = new Paragraph(writer.toString());
        p.setAlignment(Element.ALIGN_JUSTIFIED);
        output.open();
        output.add(p);
        output.close();

        assertThat(new File("./target/template.pdf")).exists();
    }
}
