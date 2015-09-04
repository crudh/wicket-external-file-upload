package se.cudh.wicket.example;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.file.FileCleaner;
import org.apache.wicket.util.io.IOUtils;
import org.apache.wicket.util.upload.DiskFileItemFactory;
import org.apache.wicket.util.upload.FileItem;
import org.apache.wicket.util.upload.FileItemFactory;
import org.apache.wicket.util.upload.FileUploadException;
import org.apache.wicket.util.upload.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		add(new Label("version", getApplication().getFrameworkSettings().getVersion()));

		// TODO Add your page's components here

    }

	@Override
	protected void onInitialize() {
		super.onInitialize();

		final HttpServletRequest request = ((HttpServletRequest) getRequest().getContainerRequest());
		final ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory(new FileCleaner()));
		try {
			final List<FileItem> fileItems = servletFileUpload.parseRequest(request);
			for (FileItem item : fileItems) {
				System.out.println("Name: " + item.getFieldName());
				if ("fileBetXml".equals(item.getFieldName())) {
					final InputStream uploadedStream = item.getInputStream();
					final StringWriter writer = new StringWriter();
					IOUtils.copy(uploadedStream, writer, "UTF-8");
					final String xml = writer.toString();
					System.out.println("XML: " + xml);
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
