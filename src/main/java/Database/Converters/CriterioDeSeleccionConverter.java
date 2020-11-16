package Database.Converters;

import domain.validadorDeTransparencia.CriterioDeSeleccionPresupuesto;
import domain.validadorDeTransparencia.CriterioMenorValorPresupuesto;
import domain.validadorDeTransparencia.DescriptorCriterioDeSeleccionPresupuesto;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CriterioDeSeleccionConverter implements AttributeConverter<CriterioDeSeleccionPresupuesto, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CriterioDeSeleccionPresupuesto criterioDeSeleccionPresupuesto) {
        return new Integer(criterioDeSeleccionPresupuesto.getDescriptor().ordinal());
    }

    @Override
    public CriterioDeSeleccionPresupuesto convertToEntityAttribute(Integer integer) {
            if(integer==0)
                return CriterioMenorValorPresupuesto.instancia();
            else
                return null;
    }

}
