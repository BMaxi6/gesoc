package DTOs;

import controllers.ArchivoController;
import domain.Egresos.DocumentoComercial;
import domain.Egresos.TipoDocumentoComercial;

import java.time.LocalDate;

public class DocumentoComercialDto {
    private String numeroDocumento;
    private String tipo;
    private String fecha;
    private String path;

    public DocumentoComercialDto() {
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public DocumentoComercial toDocumentoDomercial(){
        DocumentoComercial doc= new DocumentoComercial();
        doc.setNumeroDocumento(this.numeroDocumento);
        doc.setTipo(TipoDocumentoComercial.toTipoDocumentoComercial(this.tipo));
        doc.setPath(ArchivoController.obtenerNombreArchivo(path));
        doc.setFecha(LocalDate.parse(this.fecha));
        return doc;

    }
}
