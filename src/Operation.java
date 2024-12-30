import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

class Operation implements Serializable {
    @Getter
    @Setter
    private double nombre1;
    @Getter
    @Setter
    private double nombre2;
    @Getter
    @Setter
    private String operateur;

    public String calcule() {
        if (this.operateur.equals("+")) {
            return ("le resultat de cette operation est : " + (this.nombre1 + this.nombre2));
        } else if (this.operateur.equals("-")) {
            return ("le resultat de cette operation est : " + (this.nombre1 - this.nombre2));

        } else if (this.operateur.equals("*")) {
            return ("le resultat de cette operation est : " + (this.nombre1 * this.nombre2));

        } else if (this.operateur.equals("/")) {
            if (!(this.nombre2 == 0)){return "le resultat de cette operation est : " + (this.nombre1 / this.nombre2) ;}
            else return "Divise par 0 impossible";


        } else return "Invalid Operation";
    }


}
