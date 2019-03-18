package prodottilatteria.model;

/**
 * Classe contenente le informazioni relative ad 1 prodotto
 */


public class Product {
    private int id;
    private String bc;
    private String nome;
    private String prezzoa;
    private String prezzov;
    private String marca;

    //assegnazione valori agli attributi della classe (passati alla creazione della classe)

    public Product(int id, String bc, String nome, String prezzoa, String prezzov, String marca) {
        this.id = id;
        this.bc = bc;
        this.nome = nome;
        this.prezzoa = prezzoa;
        this.prezzov = prezzov;
        this.marca = marca;
    }

    //metodi utilizzati per ottenere le info relative ad un prodotto specifico
    public int getid() { return id; }

    public String getbc() { return bc; }

    public String getnome() { return nome; }

    public String getprezzoa() { return prezzoa; }

    public String getprezzov() { return prezzov; }

    public String getmarca() { return marca; }

    public void setbc(String bc) { this.bc = bc; }
    
    public void setnome(String nome) { this.nome = nome; }

    public void setprezzoa(String prezzoa) { this.prezzoa = prezzoa; }
    
    public void setprezzov(String prezzov) { this.prezzov = prezzov; }
    
    public void setmarca(String marca) { this.marca = marca; }

}