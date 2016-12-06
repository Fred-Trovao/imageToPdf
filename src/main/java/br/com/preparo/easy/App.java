package br.com.preparo.easy;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws FileNotFoundException, DocumentException {
		generatePDFFromImage("http://preparojuridico.com/sent_in3/Apostila%203%20-%20Turma%203/files/assets/mobile/pages/", 55, "Apostila 3");
	}

	private static void generatePDFFromImage(String urlEntrada, int quantPaginas, String nomePdf)
			throws FileNotFoundException, DocumentException {

		System.out.println("Carregando arquivos e gerando pdf...");

		Document document = new Document(PageSize.A4);
		String output = "/home/fred/Marcal/" + nomePdf + ".pdf";
		FileOutputStream fos = new FileOutputStream(output);

		PdfWriter writer = PdfWriter.getInstance(document, fos);
		writer.open();
		document.open();

		int erros = 0;
		for (int i = 1; i <= quantPaginas; i++) {

			System.out.print(".");

			String pagina = "" + i;

			while (pagina.length() < 4) {
				pagina = "0" + pagina;
			}

			String input = urlEntrada + "page" + pagina + "_i2.jpg";

			Image image = null;
			try {
				image = Image.getInstance((new URL(input)));
			} catch (IOException e) {
				System.out.println("Falha ao criar imagem de número :" + i);
				erros++;

				continue;
			}

			int indentation = 0;

			float scaler = ((document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin()
					- indentation) / image.getWidth()) * 100;

			image.scalePercent(scaler);

			document.add(image);
		}

		document.close();
		writer.close();

		System.out.println();
		System.out.println("Finalizado...");
		System.out.println("Sucesso: " + (quantPaginas - erros));
		System.out.println("Falhas: " + erros);
	}
}