package DTOs;

import domain.Items.Item;
import domain.Items.ItemPresupuesto;
import domain.Items.TipoDeItem;

public class ItemPresupuestoDto {
    private int id;
    private Integer cantidad;
    private Double valorUnitario;
    private String tipo;
    private ItemDto itemAsociado;

    public ItemPresupuestoDto(ItemPresupuesto item){
        this.id = item.getId();
        this.cantidad = item.getCantidad();
        this.valorUnitario = item.getValorUnitario();
        if(item.getTipo().equals(TipoDeItem.PRODUCTO)){
            this.tipo = "Producto";
        }else{
            this.tipo = "Servicio";
        }
        this.itemAsociado = ItemDto.toItemDto(item.getItemAsociado());
    }

    public ItemPresupuestoDto(){}

    public static ItemPresupuestoDto toItemPresupuestoDto(ItemPresupuesto item){
        return new ItemPresupuestoDto(item);
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

    public ItemDto getItemAsociado() {
        return itemAsociado;
    }

    public void setItemAsociado(ItemDto itemAsociado) {
        this.itemAsociado = itemAsociado;
    }
}
