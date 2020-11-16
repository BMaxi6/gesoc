package DTOs;

import domain.Items.Item;
import domain.Items.TipoDeItem;

public class ItemDto {
    private int id;
    private Integer cantidad;
    private Double valorUnitario;
    private String tipo;
    private String descripcion;

    public ItemDto(Item item){
        this.id = item.getId();
        this.cantidad = item.getCantidad();
        this.valorUnitario = item.getValorUnitario();
        if(item.getTipo().equals(TipoDeItem.PRODUCTO)){
            this.tipo = "Producto";
        }else{
            this.tipo = "Servicio";
        }
        this.descripcion = item.getDescripcion();
    }

    public ItemDto(){}

    public static ItemDto toItemDto(Item item){
        return new ItemDto(item);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
