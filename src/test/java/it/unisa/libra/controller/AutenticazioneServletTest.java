package it.unisa.libra.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import it.unisa.libra.util.Actions;
import it.unisa.libra.util.JspPagesIndex;

public class AutenticazioneServletTest {

  @Mock
  private HttpServletRequest request;
  @Mock
  private HttpServletResponse response;
  @Mock
  private HttpSession session;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void logoutTest() throws Exception {

    when(request.getParameter(Actions.ACTION)).thenReturn(Actions.LOGOUT);
    when(request.getSession()).thenReturn(session);

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);

    when(response.getWriter()).thenReturn(pw);

    new AutenticazioneServlet().doPost(request, response);

    verify(response).sendRedirect(request.getContextPath() + JspPagesIndex.HOME);
  }

  @Test
  public void invaliActionTest() throws Exception {
    when(request.getParameter(Actions.ACTION)).thenReturn("");
    when(request.getSession()).thenReturn(session);

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);

    when(response.getWriter()).thenReturn(pw);

    new AutenticazioneServlet().doGet(request, response);

    verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);

  }
}