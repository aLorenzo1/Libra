package it.unisa.libra.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unisa.libra.bean.FeedbackPK;
import it.unisa.libra.bean.ProgettoFormativo;
import it.unisa.libra.bean.Report;
import it.unisa.libra.bean.ReportPK;
import it.unisa.libra.bean.Studente;
import it.unisa.libra.model.dao.IProgettoFormativoDao;
import it.unisa.libra.model.dao.IReportDao;
import it.unisa.libra.model.dao.IStudenteDao;
import it.unisa.libra.util.Actions;

/**
 * Servlet implementation class AutenticazioneServlet. Controller Class che
 * gestisce l'aggiunta e la modifica dei report periodici da parte dello
 * Studente.
 * 
 * @author Giandomenico Solimando
 * @author Lucio Giordano
 * 
 * @version 1.0
 * 
 * 
 */
@WebServlet(name = "ReportStudenteServlet", urlPatterns = "/ReportStudenteServlet")
public class ReportStudenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/** gestore della persistenza per l'entit� Report. **/
	@EJB
	private IReportDao reportDao;

	/** gestore della persistenza per l'entit� Progetto Formativo. **/
	@EJB
	private IProgettoFormativoDao progettoFormativoDao;

	/** gestore della persistenza per l'entit� Studente. **/
	@EJB
	private IStudenteDao studenteDao;

	/** Default constructor. */
	public ReportStudenteServlet() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().write(BADREQUEST_MESS);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		/*
		 * String action = request.getParameter(Actions.ACTION); if (action == null) {
		 * response.getWriter().write(BADREQUEST_MESS); return; } if
		 * (action.equals(Actions.MODIFICA_REPORT)) { modificaReport(request, response);
		 * return; }
		 */
		if (action.equals("aggiungiReport")) {
			aggiungiReport(request, response);
			return;
		} else {
			response.getWriter().write("nulla");
		}
	}

	/*
	 * private void modificaReport(HttpServletRequest request, HttpServletResponse
	 * response) throws IOException, ServletException { String emailStudente =
	 * (String) request.getSession().getAttribute("utenteEmail"); String
	 * idProgettoFormativo = (String) request.getParameter("idPF");
	 * 
	 * int id; try { id = Integer.parseInt(idProgettoFormativo); } catch
	 * (NumberFormatException e) {
	 * response.getWriter().write("Si e' verificato un errore"); return; }
	 * 
	 * Studente studente = studenteDao.findById(Studente.class, emailStudente);
	 * ProgettoFormativo progettoFormativo =
	 * progettoFormativoDao.findById(ProgettoFormativo.class, id);
	 * 
	 * if ((studente == null) || (progettoFormativo == null)) {
	 * 
	 * response.getWriter().write("Si e' verificato un errore"); return; } String
	 * indiceReport = (String) request.getParameter("indiceReport");
	 * 
	 * if (indiceReport == null) { response.getWriter().write(BADREQUEST_MESS);
	 * return; }
	 * 
	 * int indice; try { indice = Integer.parseInt(indiceReport); } catch
	 * (NumberFormatException e) {
	 * response.getWriter().write("Si e' verificato un errore"); return; }
	 * 
	 * String testoReport = (String) request.getParameter("testoReport"); if
	 * (testoReport == null) { response.getWriter().write(BADREQUEST_MESS); return;
	 * }
	 * 
	 * List<Report> listReport = progettoFormativo.getReports();
	 * listReport.get(indice).setTesto(testoReport);
	 * progettoFormativoDao.persist(progettoFormativo);
	 * response.getWriter().write(SUCCESS_MESS); return; }
	 */
	private void aggiungiReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String emailStudente = (String) request.getSession().getAttribute("utenteEmail");
		Studente studente = studenteDao.findById(Studente.class, emailStudente);
		ProgettoFormativo progettoFormativo = progettoFormativoDao.getLastProgettoFormativoByStudente(studente);
		if (studente == null) {
			response.getWriter().write("errore");
			return;
		} else if (progettoFormativo == null) {
			response.getWriter().write("errorePf");
		}
		// OK, FIN QUI FUNZIONA
		String testoNuovoReport = (String) request.getParameter("testoNuovoReport");
		try {
			Report report = new Report();
			ReportPK rep = new ReportPK();
			rep.setProgettoFormativoID(progettoFormativo.getId());
			report.setTesto(testoNuovoReport);
			report.setId(rep);
			//report.setProgettoFormativo(progettoFormativo);	//ULTIMA MODIFICA 16.40
			progettoFormativo.addReport(report);
			progettoFormativoDao.persist(progettoFormativo);
			
			reportDao.persist(report);
			response.getWriter().write("finito");
		} catch (Exception ex) {
			response.getWriter().write("errore2");
			ex.printStackTrace();
		}

	}

	/** messaggio di errore inviato in caso di bad request. **/
	private static final String BADREQUEST_MESS = "L'operazione richiesta non e' valida.";

	/** messaggio restituito in caso di successo dell'operazione. **/
	private static final String SUCCESS_MESS = "ok";
}
