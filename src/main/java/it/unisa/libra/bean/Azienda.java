package it.unisa.libra.bean;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * The persistent class for the azienda database table.
 * 
 */
@Entity

@NamedQueries({@NamedQuery(name = "Azienda.findAll", query = "SELECT a FROM Azienda a"),
    @NamedQuery(name = "Azienda.findName",
        query = "SELECT a FROM Azienda a WHERE a.nome=:nomeAzienda"),
    @NamedQuery(name = "Azienda.count", query = "SELECT COUNT(a) FROM Azienda a")})

public class Azienda implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  private String utenteEmail;

  private String nome;

  private String partitaIVA;

  private String sede;

  // bi-directional one-to-one association to Utente
  @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
  @MapsId
  @JoinColumn(name = "utenteEmail")
  private Utente utente;

  // bi-directional many-to-many association to Studente
  @ManyToMany(mappedBy = "aziende")
  private List<Studente> studenti;

  // bi-directional many-to-one association to ProgettoFormativo
  @OneToMany(mappedBy = "azienda")
  private List<ProgettoFormativo> progettiFormativi;

  // bi-directional many-to-one association to TutorEsterno
  @OneToMany(mappedBy = "azienda", cascade = {CascadeType.ALL})
  private List<TutorEsterno> tutorEsterni;

  public Azienda() {}

  public String getUtenteEmail() {
    return this.utenteEmail;
  }

  public void setUtenteEmail(String utenteEmail) {
    this.utenteEmail = utenteEmail;
  }

  public String getNome() {
    return this.nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getPartitaIVA() {
    return this.partitaIVA;
  }

  public void setPartitaIVA(String partitaIVA) {
    this.partitaIVA = partitaIVA;
  }

  public String getSede() {
    return this.sede;
  }

  public void setSede(String sede) {
    this.sede = sede;
  }

  public Utente getUtente() {
    return this.utente;
  }

  public void setUtente(Utente utente) {
    this.utente = utente;
  }

  public List<Studente> getStudenti() {
    return this.studenti;
  }

  public void setStudenti(List<Studente> studenti) {
    this.studenti = studenti;
  }

  public List<ProgettoFormativo> getProgettiFormativi() {
    return this.progettiFormativi;
  }

  public void setProgettiFormativi(List<ProgettoFormativo> progettiFormativi) {
    this.progettiFormativi = progettiFormativi;
  }

  /** Aggiunge un progetto formativo.
   * 
   * @param progettiFormativi il progetto da aggiungere
   * @return il progetto aggiunto
   */
  public ProgettoFormativo addProgettiFormativi(ProgettoFormativo progettiFormativi) {
    getProgettiFormativi().add(progettiFormativi);
    progettiFormativi.setAzienda(this);

    return progettiFormativi;
  }

  /** Rimuove un progetto formativo.
   * 
   * @param progettiFormativi il progetto da rimuovere
   * @return il progetto rimosso
   */
  public ProgettoFormativo removeProgettiFormativi(ProgettoFormativo progettiFormativi) {
    getProgettiFormativi().remove(progettiFormativi);
    progettiFormativi.setAzienda(null);

    return progettiFormativi;
  }

  public List<TutorEsterno> getTutorEsterni() {
    return this.tutorEsterni;
  }

  public void setTutorEsterni(List<TutorEsterno> tutorEsterni) {
    this.tutorEsterni = tutorEsterni;
  }

  /** Aggiunge un tutor esterno.
   * 
   * @param tutorEsterno il tutor da aggiungere
   * @return il tutor aggiunto
   */
  public TutorEsterno addTutorEsterno(TutorEsterno tutorEsterno) {
    getTutorEsterni().add(tutorEsterno);
    // tutorEsterno.setAzienda(this);

    return tutorEsterno;
  }

  /** Rimuove un tutor esterno.
   * 
   * @param tutorEsterno il tutor da rimuovere
   * @return il tutor rimosso
   */
  public TutorEsterno removeTutorEsterno(TutorEsterno tutorEsterno) {
    getTutorEsterni().remove(tutorEsterno);
    // tutorEsterno.setAzienda(null);

    return tutorEsterno;
  }

}
