package PersistenciaHibernate;

import ApiMercadoLibre.ServicioMl;
import domain.Categorizacion.Categoria;
import domain.Categorizacion.Criterio;
import domain.Egresos.Ingreso;
import domain.Egresos.OperacionDeEgreso;
import domain.Egresos.Presupuesto;
import domain.EntidadesOrganizacionales.CategoriaEmpresa.Rubro;
import domain.EntidadesOrganizacionales.Empresa;
import domain.EntidadesOrganizacionales.EntidadBase;
import domain.EntidadesOrganizacionales.EntidadJuridica;
import domain.EntidadesOrganizacionales.Organizacion;
import domain.Items.Item;
import domain.Items.ItemPresupuesto;
import domain.Pagos.*;
import domain.Personas.Administrador;
import domain.Personas.Proveedor;
import domain.Personas.Usuario;
import domain.monedas.Moneda;
import domain.ubicaciones.Direccion;
import domain.ubicaciones.Pais;
import domain.validadorDeTransparencia.CriterioMenorValorPresupuesto;
import org.junit.Test;
import repositorios.FactoryRepositorio;
import repositorios.Repositorio;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static domain.Items.TipoDeItem.PRODUCTO;
import static domain.Items.TipoDeItem.SERVICIO;

public class CargaDatosPrueba {
    /*
    @Test
    public void cargarCasosDePrueba() throws IOException {
        Repositorio<Pais> repoPaises = FactoryRepositorio.instancia().obtenerRepositorio(Pais.class);
        Repositorio<Moneda> repoMonedas = FactoryRepositorio.instancia().obtenerRepositorio(Moneda.class);
        Repositorio<Organizacion> repoOrganizaciones = FactoryRepositorio.instancia().obtenerRepositorio(Organizacion.class);
        Repositorio<Empresa> repoEmpresas = FactoryRepositorio.instancia().obtenerRepositorio(Empresa.class);
        Repositorio<Usuario> repoUsuarios = FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class);
        Repositorio<OperacionDeEgreso> repoEgresos = FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class);
        Repositorio<Ingreso> repoIngresos = FactoryRepositorio.instancia().obtenerRepositorio(Ingreso.class);
        Repositorio<Categoria> repoCategorias = FactoryRepositorio.instancia().obtenerRepositorio(Categoria.class);
        Repositorio<Administrador> repoAdmin = FactoryRepositorio.instancia().obtenerRepositorio(Administrador.class);

        Administrador admin = new Administrador();
        admin.setContrasenia("1234");
        admin.setNombre("marcos");
        admin.setNombreReal("Marcos");
        admin.setApellido("Infantino");

        Administrador admin1 = new Administrador();
        admin1.setContrasenia("5678");
        admin1.setNombre("mari");
        admin1.setNombreReal("Marina");
        admin1.setApellido("Kevorkyan");

        Administrador admin2 = new Administrador();
        admin2.setContrasenia("9101");
        admin2.setNombre("maxi");
        admin2.setNombreReal("Maximiliano");
        admin2.setApellido("Brun");


        repoAdmin.agregar(admin);
        repoAdmin.agregar(admin1);
        repoAdmin.agregar(admin2);

        List<Pais> paises = ServicioMl.instancia().listaPaises();
        paises.forEach(pais -> repoPaises.agregar(pais));
        List<Moneda> monedas = ServicioMl.instancia().obtenerMonedas();
        monedas.forEach(m -> repoMonedas.agregar(m));

        Moneda PesoArgentino = monedas.stream().filter(m -> m.getIdMoneda().equals("ARS")).findFirst().get();

        Empresa EAAF_BA = new Empresa();
        EAAF_BA.setRazonSocial("EAAF BA");
        EAAF_BA.setCuit("30-15269857-2");
        Direccion direEaafBa = new Direccion();
        direEaafBa.setCalle("Av. Medrano");
        direEaafBa.setAltura(951);
        direEaafBa.setPais(paises.stream().filter(p -> p.getNombre().equals("Argentina")).findFirst().get());
        direEaafBa.setProvincia(direEaafBa.getPais().getProvincias().stream().filter(provincia -> provincia.getNombre().equals("Capital Federal")).findFirst().get());
        direEaafBa.setCiudad(direEaafBa.getProvincia().getCiudades().stream().filter(ciudad -> ciudad.getNombre().equals("Capital Federal")).findFirst().get());//deberia poner almagro pero no esta en la api
        EAAF_BA.setDireccion(direEaafBa);
        EAAF_BA.setCantidadPersonal(150);
        EAAF_BA.setActividad(Rubro.CONSTRUCCION);
        EAAF_BA.setPromedioVentas(600000000.00);
        EAAF_BA.setNombreFicticio("Oficina central Buenos Aires");
        repoEmpresas.agregar(EAAF_BA);

        Empresa EAAF_NY = new Empresa();
        EAAF_NY.setRazonSocial("EAAF NY");
        EAAF_NY.setCuit("30-15789655-7");
        Direccion direEaafNy = new Direccion();
        direEaafNy.setCalle("Liberty Ave");
        direEaafNy.setAltura(720);
        direEaafNy.setPais(paises.stream().filter(p -> p.getNombre().equals("Uruguay")).findFirst().get());///////me pide nueva yor pero no esta en ML
        direEaafNy.setProvincia(direEaafNy.getPais().getProvincias().stream().filter(provincia -> provincia.getNombre().equals("Montevideo")).findFirst().get());
        direEaafNy.setCiudad(direEaafNy.getProvincia().getCiudades().get(0));//deberia poner almagro pero no esta en la api
        EAAF_NY.setDireccion(direEaafNy);
        EAAF_NY.setCantidadPersonal(580);
        EAAF_NY.setActividad(Rubro.CONSTRUCCION);
        EAAF_NY.setPromedioVentas(960000000.00);
        EAAF_NY.setNombreFicticio("Oficina central Nueva York");
        repoEmpresas.agregar(EAAF_NY);

        Empresa EAAF_M = new Empresa();
        EAAF_M.setRazonSocial("EAAF M");
        EAAF_M.setCuit("30-77896583-9");
        Direccion direEaafM = new Direccion();
        direEaafM.setCalle("Roberto Gayol");
        direEaafM.setAltura(55);
        direEaafM.setPais(paises.stream().filter(p -> p.getNombre().equals("Mexico")).findFirst().get());///////me pide nueva yor pero no esta en ML
        direEaafM.setProvincia(direEaafM.getPais().getProvincias().get(0));
        direEaafM.setCiudad(direEaafM.getProvincia().getCiudades().get(0));//deberia poner almagro pero no esta en la api
        EAAF_M.setDireccion(direEaafM);
        EAAF_M.setCantidadPersonal(240);
        EAAF_M.setActividad(Rubro.CONSTRUCCION);
        EAAF_M.setPromedioVentas(643710000.00);
        EAAF_M.setNombreFicticio("Oficina central Nueva York");
        repoEmpresas.agregar(EAAF_M);

        Organizacion EquipoAntropologia = new Organizacion();
        EquipoAntropologia.setNombre("Equipo Argentino de Antropología Forense - EAAF");
        EquipoAntropologia.agregarEntidadJuridica(EAAF_BA);
        EquipoAntropologia.agregarEntidadJuridica(EAAF_NY);
        EquipoAntropologia.agregarEntidadJuridica(EAAF_M);
        repoOrganizaciones.agregar(EquipoAntropologia);

        Empresa SurcosCs = new Empresa();
        SurcosCs.setRazonSocial("Surcos CS");
        SurcosCs.setCuit("30-25888897-8");
        Direccion direSurcos = new Direccion();
        direSurcos.setCalle("Jerónimo Salguero");
        direSurcos.setAltura(2800);
        direSurcos.setPais(paises.stream().filter(p -> p.getNombre().equals("Argentina")).findFirst().get());///////me pide nueva yor pero no esta en ML
        direSurcos.setProvincia(direSurcos.getPais().getProvincias().stream().filter(provincia -> provincia.getNombre().equals("Capital Federal")).findFirst().get());
        direSurcos.setCiudad(direSurcos.getProvincia().getCiudades().stream().filter(ciudad -> ciudad.getNombre().equals("Capital Federal")).findFirst().get());//deberia poner almagro pero no esta en la api
        SurcosCs.setDireccion(direSurcos);
        SurcosCs.setCantidadPersonal(8);
        SurcosCs.setActividad(Rubro.SERVICIO_DE_ALOJAMIENTO);
        SurcosCs.setPromedioVentas(8000000.00);
        SurcosCs.setNombreFicticio("Surcos");
        repoEmpresas.agregar(SurcosCs);

        EntidadBase Andhes = new EntidadBase();
        Andhes.setNombre("Andhes");

        Organizacion CDIA = new Organizacion();
        CDIA.setNombre("Colectivo de Derechos de Infancia y Adolescencia - CDIA");
        CDIA.agregarEntidadJuridica(SurcosCs);
        CDIA.agregarEntidadBase(Andhes);
        repoOrganizaciones.agregar(CDIA);


        Usuario AlejandroRoco = new Usuario();
        AlejandroRoco.setNombreReal("Alejandro");
        AlejandroRoco.setApellido("Roco");
        AlejandroRoco.setOrganizacion(EAAF_BA);
        AlejandroRoco.setNombre("aroco");
        AlejandroRoco.setContrasenia("*_aroco20!-?");
        repoUsuarios.agregar(AlejandroRoco);
        EAAF_BA.agregarUsuario(AlejandroRoco);

        Usuario RocioRojas = new Usuario();
        RocioRojas.setNombreReal("Rocío");
        RocioRojas.setApellido("Rojas");
        RocioRojas.setOrganizacion(EAAF_BA);
        RocioRojas.setNombre("rrojas");
        RocioRojas.setContrasenia("*-_rrojas!?");
        repoUsuarios.agregar(RocioRojas);
        EAAF_BA.agregarUsuario(RocioRojas);

        Usuario JulietaAzul = new Usuario();
        JulietaAzul.setNombreReal("Julieta");
        JulietaAzul.setApellido("Azul");
        JulietaAzul.setOrganizacion(SurcosCs);
        JulietaAzul.setNombre("jazul");
        JulietaAzul.setContrasenia("!-*jazul_!?");
        repoUsuarios.agregar(JulietaAzul);
        SurcosCs.agregarUsuario(JulietaAzul);

        repoEmpresas.modificar(EAAF_BA);
        repoEmpresas.modificar(SurcosCs);

        //--------------------CRITERIO Y CATEGORIAS EAAF BA
        Criterio GastosMantenimiento = new Criterio();
        GastosMantenimiento.setNombre("Gastos Mantenimiento");

        Categoria Fachada = new Categoria();
        Fachada.setNombre("Fachada");
        Fachada.setDescripcion(" ");
        repoCategorias.agregar(Fachada);
        GastosMantenimiento.agregarCategoria(Fachada);


        Criterio LugarDeAplicacion = new Criterio();
        LugarDeAplicacion.setNombre("Lugar de aplicación");
        GastosMantenimiento.agregarSubCriterio(LugarDeAplicacion);


        Categoria Interior = new Categoria();
        Interior.setNombre("Interior");
        Interior.setDescripcion(" ");
        repoCategorias.agregar(Interior);
        LugarDeAplicacion.agregarCategoria(Interior);

        Criterio Causante = new Criterio();
        Causante.setNombre("Causante");

        Categoria Humedad = new Categoria();
        Humedad.setNombre("Humedad");
        Humedad.setDescripcion(" ");
        repoCategorias.agregar(Humedad);
        Causante.agregarCategoria(Humedad);

        Criterio GastosGenerales = new Criterio();
        GastosGenerales.setNombre("Gastos generales");

        Categoria ServiciosGenerales = new Categoria();
        ServiciosGenerales.setNombre("Servicios generales");
        ServiciosGenerales.setDescripcion(" ");
        repoCategorias.agregar(ServiciosGenerales);
        GastosGenerales.agregarCategoria(ServiciosGenerales);

        Criterio ElementosOficina = new Criterio();
        ElementosOficina.setNombre("Elementos de oficina");

        Categoria MueblesYUtilles = new Categoria();
        MueblesYUtilles.setNombre("Muebles y útiles");
        MueblesYUtilles.setDescripcion(" ");
        repoCategorias.agregar(MueblesYUtilles);
        ElementosOficina.agregarCategoria(MueblesYUtilles);

        Criterio MomentoUtilizacion = new Criterio();
        MomentoUtilizacion.setNombre("Momento de utilización");

        Categoria CoffeBreak = new Categoria();
        CoffeBreak.setNombre("Coffe Break");
        CoffeBreak.setDescripcion(" ");
        repoCategorias.agregar(CoffeBreak);
        MomentoUtilizacion.agregarCategoria(CoffeBreak);

        Criterio TipoProducto = new Criterio();
        TipoProducto.setNombre("Tipo de producto");

        Categoria Electronicos = new Categoria();
        Electronicos.setNombre("Electrónicos");
        Electronicos.setDescripcion(" ");
        repoCategorias.agregar(Electronicos);
        TipoProducto.agregarCategoria(Electronicos);

        Categoria Exterior = new Categoria();
        Exterior.setNombre("Exterior");
        Exterior.setDescripcion(" ");
        repoCategorias.agregar(Exterior);
        LugarDeAplicacion.agregarCategoria(Exterior);

        Criterio TamanioGasto = new Criterio();
        TamanioGasto.setNombre("Tamaño del gasto");

        Categoria Grande = new Categoria();
        Grande.setNombre("Grande");
        Grande.setDescripcion(" ");
        repoCategorias.agregar(Grande);
        TamanioGasto.agregarCategoria(Grande);


        EAAF_BA.agregarCriterio(GastosMantenimiento);
        EAAF_BA.agregarCriterio(LugarDeAplicacion);
        EAAF_BA.agregarCriterio(Causante);
        EAAF_BA.agregarCriterio(GastosGenerales);
        EAAF_BA.agregarCriterio(ElementosOficina);
        EAAF_BA.agregarCriterio(MomentoUtilizacion);
        EAAF_BA.agregarCriterio(TipoProducto);
        EAAF_BA.agregarCriterio(TamanioGasto);

        repoEmpresas.modificar(EAAF_BA);

        //------------------------------CRITERIOS Y CATEGORIAS DE SURCOS
        Criterio Servicios = new Criterio();
        Servicios.setNombre("Servicios");

        Categoria ServiciosLuz = new Categoria();
        ServiciosLuz.setNombre("Servicios de Luz");
        ServiciosLuz.setDescripcion(" ");
        repoCategorias.agregar(ServiciosLuz);
        Servicios.agregarCategoria(ServiciosLuz);

        Categoria ServiciosGas = new Categoria();
        ServiciosGas.setNombre("Servicios de Gas");
        ServiciosGas.setDescripcion(" ");
        repoCategorias.agregar(ServiciosGas);
        Servicios.agregarCategoria(ServiciosGas);

        Criterio ElementosUsoInterno = new Criterio();
        ElementosUsoInterno.setNombre("Elementos de uso interno");

        Categoria Necesarios = new Categoria();
        Necesarios.setNombre("Necesarios");
        Necesarios.setDescripcion(" ");
        repoCategorias.agregar(Necesarios);
        ElementosUsoInterno.agregarCategoria(Necesarios);

        SurcosCs.agregarCriterio(Servicios);
        SurcosCs.agregarCriterio(ElementosUsoInterno);

        repoEmpresas.modificar(SurcosCs);

        //---------------------------Proveedores
        Proveedor PintureriasSerrentino = new Proveedor();
        PintureriasSerrentino.setNombre("Pinturerias Serrentino");
        EAAF_BA.agregarProveedor(PintureriasSerrentino);

        Proveedor EdesurEAAF = new Proveedor();
        EdesurEAAF.setNombre("Edesur");
        EAAF_BA.agregarProveedor(EdesurEAAF);

        Proveedor EdesurSurcos = new Proveedor();
        EdesurSurcos.setNombre("Edesur");
        SurcosCs.agregarProveedor(EdesurSurcos);

        Proveedor MetrogasEAAF = new Proveedor();
        MetrogasEAAF.setNombre("Metrogas");
        EAAF_BA.agregarProveedor(MetrogasEAAF);

        Proveedor MetrogasSurcos = new Proveedor();
        MetrogasSurcos.setNombre("Metrogas");
        SurcosCs.agregarProveedor(MetrogasSurcos);


        Proveedor MitoasSa = new Proveedor();
        MitoasSa.setNombre("Mitoas SA");
        EAAF_BA.agregarProveedor(MitoasSa);

        Proveedor IngenieriaComercialSrl = new Proveedor();
        IngenieriaComercialSrl.setNombre("Ingenieria comercial SRL");
        EAAF_BA.agregarProveedor(IngenieriaComercialSrl);

        Proveedor CorralonLapridaSrl = new Proveedor();
        CorralonLapridaSrl.setNombre("Corralón Laprida SRL");
        EAAF_BA.agregarProveedor(CorralonLapridaSrl);

        Proveedor TelasZn = new Proveedor();
        TelasZn.setNombre("Telas ZN");
        SurcosCs.agregarProveedor(TelasZn);

        Proveedor PintureriasRex = new Proveedor();
        PintureriasRex.setNombre("Pinturerías REX");
        EAAF_BA.agregarProveedor(PintureriasRex);

        Proveedor PintureriasSanJorge = new Proveedor();
        PintureriasSanJorge.setNombre("Pinturerías San Jorge");
        EAAF_BA.agregarProveedor(PintureriasSanJorge);

        Proveedor CasaAudio = new Proveedor();
        CasaAudio.setNombre("La Casa del Audio");
        EAAF_BA.agregarProveedor(CasaAudio);

        Proveedor Garbarino = new Proveedor();
        Garbarino.setNombre("Garbarino");
        EAAF_BA.agregarProveedor(Garbarino);

        Proveedor CorralonSanJuan = new Proveedor();
        CorralonSanJuan.setNombre("Corralón San Juan SRL");
        EAAF_BA.agregarProveedor(CorralonSanJuan);

        repoEmpresas.modificar(EAAF_BA);
        repoEmpresas.modificar(SurcosCs);

        //--------------------------------EGRESOS

        //-------------------------------EGRESO1
        OperacionDeEgreso egreso1 = new OperacionDeEgreso();
        LocalDate fecha = LocalDate.of(2020, 3, 10);
        egreso1.setFecha(fecha);

        Item item11 = new Item();
        item11.setCantidad(1);
        item11.setDescripcion("PINTURA Z10 LATEX SUPERCUBRITIVO 20L");
        item11.setTipo(PRODUCTO);
        item11.setValorUnitario(9625.00);
        egreso1.agregarItem(item11);

        Item item12 = new Item();
        item12.setCantidad(1);
        item12.setDescripcion("PINTURA LOXON FTES IMPERMEABILIZANTE 10L");
        item12.setTipo(PRODUCTO);
        item12.setValorUnitario(6589.40);
        egreso1.agregarItem(item12);

        Item item13 = new Item();
        item13.setCantidad(1);
        item13.setDescripcion("PINTURA BRIKOL PISOS NEGRO 4L");
        item13.setTipo(PRODUCTO);
        item13.setValorUnitario(3738.29);
        egreso1.agregarItem(item13);

        MedioDePago medioDePago1 = new TarjetaCredito("4509 9535 6623 3704", OpcionDePago.TRES_PAGOS, TipoDePago.SIN_INTERES);
        Pago pago1 = new Pago();
        pago1.setMedioDePago(medioDePago1);
        pago1.setMontoPago(egreso1.getValorTotal());
        pago1.setDato(" ");
        pago1.setMoneda(PesoArgentino);
        pago1.setNumeroPago(1);

        egreso1.setNumeroOp(1);
        egreso1.setMoneda(PesoArgentino);
        egreso1.setPago(pago1);
        egreso1.setCriterioSelecc(CriterioMenorValorPresupuesto.instancia());
        egreso1.setOrganizacion(EAAF_BA);
        egreso1.setProveedor(PintureriasSerrentino);
        egreso1.agregarRevisor(AlejandroRoco);
        egreso1.setCantidadPresupuestosRequeridos(3);

        egreso1.agregarCategoria(Fachada);
        egreso1.agregarCategoria(Interior);
        egreso1.agregarCategoria(Humedad);

        repoEgresos.agregar(egreso1);

        //---------------------------------------EGRESO2
        OperacionDeEgreso egreso2 = new OperacionDeEgreso();
        LocalDate fecha2 = LocalDate.of(2020, 7, 8);
        egreso2.setFecha(fecha2);

        Item item21 = new Item();
        item21.setCantidad(1);
        item21.setDescripcion("FACTURA SERVICIO DE LUZ PERIODO JUNIO 2020");
        item21.setTipo(SERVICIO);
        item21.setValorUnitario(2100.0);
        egreso2.agregarItem(item21);

        MedioDePago medioDePago2 = new Efectivo();
        Pago pago2 = new Pago();
        pago2.setMedioDePago(medioDePago2);
        pago2.setMontoPago(egreso2.getValorTotal());
        pago2.setDato(" ");
        pago2.setMoneda(PesoArgentino);
        pago2.setNumeroPago(2);

        egreso2.setNumeroOp(2);
        egreso2.setMoneda(PesoArgentino);
        egreso2.setPago(pago2);
        egreso2.setCriterioSelecc(CriterioMenorValorPresupuesto.instancia());
        egreso2.setOrganizacion(EAAF_BA);
        egreso2.setProveedor(EdesurEAAF);
        egreso2.agregarRevisor(AlejandroRoco);
        egreso2.setCantidadPresupuestosRequeridos(0);

        egreso2.agregarCategoria(ServiciosGenerales);

        repoEgresos.agregar(egreso2);

        //--------------------------------------EGRESO3
        OperacionDeEgreso egreso3 = new OperacionDeEgreso();

        LocalDate fecha3 = LocalDate.of(2020, 7, 9);
        egreso3.setFecha(fecha3);

        Item item31 = new Item();
        item31.setCantidad(1);
        ;
        item31.setDescripcion("FACTURA SERVICIO DE GAS PERIODO JUNIO 2020");
        item31.setTipo(SERVICIO);
        item31.setValorUnitario(3500.0);
        egreso3.agregarItem(item31);

        MedioDePago medioDePago3 = new Efectivo();
        Pago pago3 = new Pago();
        pago3.setMedioDePago(medioDePago3);
        pago3.setMontoPago(egreso3.getValorTotal());
        pago3.setDato(" ");
        pago3.setMoneda(PesoArgentino);
        pago3.setNumeroPago(3);

        egreso3.setNumeroOp(3);
        egreso3.setMoneda(PesoArgentino);
        egreso3.setPago(pago3);
        egreso3.setCriterioSelecc(CriterioMenorValorPresupuesto.instancia());
        egreso3.setOrganizacion(EAAF_BA);
        egreso3.setProveedor(EdesurEAAF);
        egreso3.agregarRevisor(AlejandroRoco);
        egreso3.setCantidadPresupuestosRequeridos(0);

        egreso3.agregarCategoria(ServiciosGenerales);

        repoEgresos.agregar(egreso3);

        //--------------------------------------EGRESO4
        OperacionDeEgreso egreso4 = new OperacionDeEgreso();

        LocalDate fecha4 = LocalDate.of(2020, 8, 2);
        egreso4.setFecha(fecha4);

        Item item41 = new Item();
        item41.setCantidad(3);
        item41.setDescripcion("PAVA ELECTRICA SMARTLIFE 1,5 LTS 1850W");
        item41.setValorUnitario(4500.0);
        item41.setTipo(PRODUCTO);
        egreso4.agregarItem(item41);

        Item item42 = new Item();
        item42.setCantidad(2);
        item42.setDescripcion("CAFETERA SMARTLIFE 1095 ACERO INOX");
        item42.setValorUnitario(6300.0);
        item42.setTipo(PRODUCTO);
        egreso4.agregarItem(item42);

        MedioDePago medioDePago4 = new TarjetaDebito("5031 7557 3453");
        Pago pago4 = new Pago();
        pago4.setMedioDePago(medioDePago4);
        pago4.setMontoPago(egreso4.getValorTotal());
        pago4.setDato(" ");
        pago4.setMoneda(PesoArgentino);
        pago4.setNumeroPago(4);

        egreso4.setNumeroOp(4);
        egreso4.setMoneda(PesoArgentino);
        egreso4.setPago(pago4);
        egreso4.setCriterioSelecc(CriterioMenorValorPresupuesto.instancia());
        egreso4.setOrganizacion(EAAF_BA);
        egreso4.setProveedor(MitoasSa);
        egreso4.agregarRevisor(AlejandroRoco);
        egreso4.setCantidadPresupuestosRequeridos(0);

        egreso4.agregarCategoria(MueblesYUtilles);
        egreso4.agregarCategoria(CoffeBreak);

        repoEgresos.agregar(egreso4);

        //-----------------------------------EGRESO5
        OperacionDeEgreso egreso5 = new OperacionDeEgreso();

        LocalDate fecha5 = LocalDate.of(2020, 9, 27);
        egreso5.setFecha(fecha5);

        Item item51 = new Item();
        item51.setCantidad(2);
        item51.setDescripcion("TELEFONOS INALAMBRICOS MOTOROLA DUO BLANCO");
        item51.setValorUnitario(8500.0);
        item51.setTipo(PRODUCTO);
        egreso5.agregarItem(item51);

        MedioDePago medioDePago5 = new Efectivo();
        Pago pago5 = new Pago();
        pago5.setMedioDePago(medioDePago5);
        pago5.setMontoPago(egreso5.getValorTotal());
        pago5.setDato(" ");
        pago5.setMoneda(PesoArgentino);
        pago5.setNumeroPago(5);

        egreso5.setNumeroOp(5);
        egreso5.setMoneda(PesoArgentino);
        egreso5.setPago(pago5);
        egreso5.setCriterioSelecc(CriterioMenorValorPresupuesto.instancia());
        egreso5.setOrganizacion(EAAF_BA);
        egreso5.setProveedor(IngenieriaComercialSrl);
        egreso5.agregarRevisor(AlejandroRoco);
        egreso5.setCantidadPresupuestosRequeridos(6);

        egreso5.agregarCategoria(MueblesYUtilles);
        egreso5.agregarCategoria(CoffeBreak);

        repoEgresos.agregar(egreso5);

        //------------------------------------------EGRESO6
        OperacionDeEgreso egreso6 = new OperacionDeEgreso();
        LocalDate fecha6 = LocalDate.of(2020, 10, 1);
        egreso6.setFecha(fecha6);

        Item item61 = new Item();
        item61.setCantidad(10);
        item61.setDescripcion("CEMENTO LOMA NEGRA");
        item61.setValorUnitario(704.4);
        item61.setTipo(PRODUCTO);
        egreso6.agregarItem(item61);

        Item item62 = new Item();
        item62.setCantidad(5);
        item62.setDescripcion("ARENA FINA EN BOLSÓN X M30");
        item62.setValorUnitario(3100.0);
        item62.setTipo(PRODUCTO);
        egreso6.agregarItem(item62);

        Item item63 = new Item();
        item63.setCantidad(4);
        item63.setDescripcion("HIERRO ACINDAR");
        item63.setValorUnitario(891.0);
        item63.setTipo(PRODUCTO);
        egreso6.agregarItem(item63);

        Item item64 = new Item();
        item64.setCantidad(800);
        item64.setDescripcion("BLOQUE LADRILLO CEMENTO");
        item64.setValorUnitario(227.0);
        item64.setTipo(PRODUCTO);
        egreso6.agregarItem(item64);

        MedioDePago medioDePago6 = new Efectivo();
        Pago pago6 = new Pago();
        pago6.setMedioDePago(medioDePago6);
        pago6.setMontoPago(egreso6.getValorTotal());
        pago6.setDato(" ");
        pago6.setMoneda(PesoArgentino);
        pago6.setNumeroPago(6);


        egreso6.setNumeroOp(6);
        egreso6.setMoneda(PesoArgentino);
        egreso6.setPago(pago6);
        egreso6.setCriterioSelecc(CriterioMenorValorPresupuesto.instancia());
        egreso6.setOrganizacion(EAAF_BA);
        egreso6.setProveedor(CorralonLapridaSrl);
        egreso6.agregarRevisor(AlejandroRoco);
        egreso6.setCantidadPresupuestosRequeridos(4);
        egreso6.agregarCategoria(Fachada);
        egreso6.agregarCategoria(Exterior);
        egreso6.agregarCategoria(Grande);

        repoEgresos.agregar(egreso6);

        //----------------------------EGRESO7
        OperacionDeEgreso egreso7 = new OperacionDeEgreso();
        LocalDate fecha7 = LocalDate.of(2020, 10, 5);
        egreso7.setFecha(fecha7);

        Item item71 = new Item();
        item71.setCantidad(800);
        item71.setDescripcion("BLOQUE LADRILLO CEMENTO");
        item71.setValorUnitario(250.0);
        item71.setTipo(PRODUCTO);
        egreso7.agregarItem(item71);

        MedioDePago medioDePago7 = new Efectivo();
        Pago pago7 = new Pago();
        pago7.setMedioDePago(medioDePago7);
        pago7.setMontoPago(egreso7.getValorTotal());
        pago7.setDato(" ");
        pago7.setMoneda(PesoArgentino);
        pago7.setNumeroPago(7);

        egreso7.setNumeroOp(7);

        egreso7.setMoneda(PesoArgentino);
        egreso7.setPago(pago7);
        egreso7.setCriterioSelecc(CriterioMenorValorPresupuesto.instancia());
        egreso7.setOrganizacion(EAAF_BA);
        egreso7.setProveedor(CorralonLapridaSrl);
        egreso7.agregarRevisor(AlejandroRoco);
        egreso7.setCantidadPresupuestosRequeridos(0);

        egreso7.agregarCategoria(Fachada);

        repoEgresos.agregar(egreso7);


        //----------------------------egreso8
        OperacionDeEgreso egreso8 = new OperacionDeEgreso();
        LocalDate fecha8 = LocalDate.of(2020, 7, 10);
        egreso8.setFecha(fecha8);

        Item item81 = new Item();
        item81.setCantidad(1);
        item81.setDescripcion("FACTURA SERVICIO DE LUZ PERIODO JUNIO 2020");
        item81.setValorUnitario(1100.0);
        item81.setTipo(SERVICIO);
        egreso8.agregarItem(item81);

        MedioDePago medioDePago8 = new Efectivo();
        Pago pago8 = new Pago();
        pago8.setMedioDePago(medioDePago8);
        pago8.setMontoPago(egreso8.getValorTotal());
        pago8.setDato(" ");
        pago8.setMoneda(PesoArgentino);
        pago8.setNumeroPago(1);

        egreso8.setNumeroOp(1);
        egreso8.setMoneda(PesoArgentino);
        egreso8.setPago(pago8);
        egreso8.setCriterioSelecc(CriterioMenorValorPresupuesto.instancia());
        egreso8.setOrganizacion(SurcosCs);
        egreso8.setProveedor(EdesurSurcos);
        egreso8.agregarRevisor(JulietaAzul);
        egreso8.setCantidadPresupuestosRequeridos(0);

        egreso8.agregarCategoria(ServiciosLuz);

        repoEgresos.agregar(egreso8);


        //----------------------------egreso9
        OperacionDeEgreso egreso9 = new OperacionDeEgreso();
        LocalDate fecha9 = LocalDate.of(2020, 7, 10);
        egreso9.setFecha(fecha9);

        Item item91 = new Item();
        item91.setCantidad(1);
        item91.setDescripcion("FACTURA SERVICIO DE GAS PERIODO JUNIO 2020");
        item91.setValorUnitario(800.0);
        item91.setTipo(SERVICIO);
        egreso9.agregarItem(item91);

        MedioDePago medioDePago9 = new Efectivo();
        Pago pago9 = new Pago();
        pago9.setMedioDePago(medioDePago9);
        pago9.setMontoPago(egreso9.getValorTotal());
        pago9.setDato(" ");
        pago9.setMoneda(PesoArgentino);
        pago9.setNumeroPago(2);

        egreso9.setNumeroOp(2);
        egreso9.setMoneda(PesoArgentino);
        egreso9.setPago(pago9);
        egreso9.setCriterioSelecc(CriterioMenorValorPresupuesto.instancia());
        egreso9.setOrganizacion(SurcosCs);
        egreso9.setProveedor(MetrogasSurcos);
        egreso9.agregarRevisor(JulietaAzul);
        egreso9.setCantidadPresupuestosRequeridos(0);

        egreso9.agregarCategoria(ServiciosGas);

        repoEgresos.agregar(egreso9);


        //---------------------------egreso10
        OperacionDeEgreso egreso10 = new OperacionDeEgreso();
        LocalDate fecha10 = LocalDate.of(2020, 9, 25);
        egreso10.setFecha(fecha10);

        Item item101 = new Item();
        item101.setCantidad(5);
        item101.setDescripcion("CORTINAS BLACKOUT VINILICO 2 PAÑOS");
        item101.setValorUnitario(4200.0);
        item101.setTipo(PRODUCTO);
        egreso10.agregarItem(item101);

        MedioDePago medioDePago10 = new Efectivo();
        Pago pago10 = new Pago();
        pago10.setMedioDePago(medioDePago10);
        pago10.setMontoPago(egreso10.getValorTotal());
        pago10.setDato(" ");
        pago10.setMoneda(PesoArgentino);
        pago10.setNumeroPago(3);

        egreso10.setNumeroOp(3);
        egreso10.setMoneda(PesoArgentino);
        egreso10.setPago(pago10);
        egreso10.setCriterioSelecc(CriterioMenorValorPresupuesto.instancia());
        egreso10.setOrganizacion(SurcosCs);
        egreso10.setProveedor(TelasZn);
        egreso10.agregarRevisor(JulietaAzul);
        egreso10.setCantidadPresupuestosRequeridos(0);

        egreso10.agregarCategoria(Necesarios);

        repoEgresos.agregar(egreso10);

        //------------------------------------PRESUPUESTOS
        //-----------------------------------Presupuesto1
        Presupuesto presupuesto1 = new Presupuesto();
        LocalDate fechap1 = LocalDate.of(2020, 2, 25);

        presupuesto1.setFecha(fechap1);
        presupuesto1.setElegido(false);
        presupuesto1.setOperacion(egreso1);
        presupuesto1.setProveedorP(PintureriasRex);
        presupuesto1.setOrganizacion(EAAF_BA);

        ItemPresupuesto itemp11 = new ItemPresupuesto();
        itemp11.setItemAsociado(item11);
        itemp11.setCantidad(1);
        itemp11.setTipo(PRODUCTO);
        itemp11.setValorUnitario(9900.8);
        presupuesto1.agregarItem(itemp11);

        ItemPresupuesto itemp12 = new ItemPresupuesto();
        itemp12.setItemAsociado(item12);
        itemp12.setCantidad(1);
        itemp12.setTipo(PRODUCTO);
        itemp12.setValorUnitario(7200.0);
        presupuesto1.agregarItem(itemp12);

        ItemPresupuesto itemp13 = new ItemPresupuesto();
        itemp13.setItemAsociado(item13);
        itemp13.setCantidad(1);
        itemp13.setTipo(PRODUCTO);
        itemp13.setValorUnitario(4350.8);
        presupuesto1.agregarItem(itemp13);

        egreso1.agregarPresupuesto(presupuesto1);

        repoEgresos.modificar(egreso1);

        //-------------------------------------Presupuesto2
        Presupuesto presupuesto2 = new Presupuesto();
        LocalDate fechap2 = LocalDate.of(2020, 2, 26);

        presupuesto2.setFecha(fechap2);
        presupuesto2.setElegido(false);
        presupuesto2.setOperacion(egreso1);
        presupuesto2.setProveedorP(PintureriasSanJorge);
        presupuesto2.setOrganizacion(EAAF_BA);


        ItemPresupuesto itemp21 = new ItemPresupuesto();
        itemp21.setItemAsociado(item11);
        itemp21.setCantidad(1);
        itemp21.setTipo(PRODUCTO);
        itemp21.setValorUnitario(9610.5);
        presupuesto2.agregarItem(itemp21);

        ItemPresupuesto itemp22 = new ItemPresupuesto();
        itemp22.setItemAsociado(item12);
        itemp22.setCantidad(1);
        itemp22.setTipo(PRODUCTO);
        itemp22.setValorUnitario(6590.3);
        presupuesto2.agregarItem(itemp22);

        ItemPresupuesto itemp23 = new ItemPresupuesto();
        itemp23.setItemAsociado(item13);
        itemp23.setCantidad(1);
        itemp23.setTipo(PRODUCTO);
        itemp23.setValorUnitario(4100.0);
        presupuesto2.agregarItem(itemp23);

        egreso1.agregarPresupuesto(presupuesto2);

        repoEgresos.modificar(egreso1);

        //-----------------------------------------Presupuesto3
        Presupuesto presupuesto3 = new Presupuesto();
        LocalDate fechap3 = LocalDate.of(2020, 2, 27);

        presupuesto3.setFecha(fechap3);
        presupuesto3.setElegido(true);
        presupuesto3.setOperacion(egreso1);
        presupuesto3.setProveedorP(PintureriasSerrentino);
        presupuesto3.setOrganizacion(EAAF_BA);

        ItemPresupuesto itemp31 = new ItemPresupuesto();
        itemp31.setItemAsociado(item11);
        itemp31.setCantidad(1);
        itemp31.setTipo(PRODUCTO);
        itemp31.setValorUnitario(9625.0);
        presupuesto3.agregarItem(itemp31);

        ItemPresupuesto itemp32 = new ItemPresupuesto();
        itemp32.setItemAsociado(item12);
        itemp32.setCantidad(1);
        itemp32.setTipo(PRODUCTO);
        itemp32.setValorUnitario(6589.4);
        presupuesto3.agregarItem(itemp32);

        ItemPresupuesto itemp33 = new ItemPresupuesto();
        itemp33.setItemAsociado(item13);
        itemp33.setCantidad(1);
        itemp33.setTipo(PRODUCTO);
        itemp33.setValorUnitario(3738.29);
        presupuesto2.agregarItem(itemp33);

        egreso1.agregarPresupuesto(presupuesto3);

        repoEgresos.modificar(egreso1);

        //---------------------------Presupuesto4
        Presupuesto presupuesto4 = new Presupuesto();
        LocalDate fechap4 = LocalDate.of(2020, 9, 10);

        presupuesto4.setFecha(fechap4);
        presupuesto4.setElegido(false);
        presupuesto4.setOperacion(egreso5);
        presupuesto4.setProveedorP(CasaAudio);
        presupuesto4.setOrganizacion(EAAF_BA);

        ItemPresupuesto itemp41 = new ItemPresupuesto();
        itemp41.setItemAsociado(item51);
        itemp41.setCantidad(1);
        itemp41.setTipo(PRODUCTO);
        itemp41.setValorUnitario(8950.0);
        presupuesto4.agregarItem(itemp41);

        egreso5.agregarPresupuesto(presupuesto4);

        repoEgresos.modificar(egreso5);

        //----------------------------Presupuesto5
        Presupuesto presupuesto5 = new Presupuesto();
        LocalDate fechap5 = LocalDate.of(2020, 9, 11);

        presupuesto5.setFecha(fechap5);
        presupuesto5.setElegido(false);
        presupuesto5.setOperacion(egreso5);
        presupuesto5.setProveedorP(Garbarino);
        presupuesto5.setOrganizacion(EAAF_BA);

        ItemPresupuesto itemp51 = new ItemPresupuesto();
        itemp51.setItemAsociado(item51);
        itemp51.setCantidad(1);
        itemp51.setTipo(PRODUCTO);
        itemp51.setValorUnitario(8830.0);
        presupuesto5.agregarItem(itemp51);

        egreso5.agregarPresupuesto(presupuesto5);

        repoEgresos.modificar(egreso5);

        //------------------------------Presupuesto6
        Presupuesto presupuesto6 = new Presupuesto();
        LocalDate fechap6 = LocalDate.of(2020, 9, 12);

        presupuesto6.setFecha(fechap6);
        presupuesto6.setElegido(true);
        presupuesto6.setOperacion(egreso5);
        presupuesto6.setProveedorP(IngenieriaComercialSrl);
        presupuesto6.setOrganizacion(EAAF_BA);

        ItemPresupuesto itemp61 = new ItemPresupuesto();
        itemp61.setItemAsociado(item51);
        itemp61.setCantidad(1);
        itemp61.setTipo(PRODUCTO);
        itemp61.setValorUnitario(8500.0);
        presupuesto6.agregarItem(itemp61);

        egreso5.agregarPresupuesto(presupuesto6);

        repoEgresos.modificar(egreso5);

        //-----------------------------Presupuesto7
        Presupuesto presupuesto7 = new Presupuesto();
        LocalDate fechap7 = LocalDate.of(2020, 9, 15);

        presupuesto7.setFecha(fechap7);
        presupuesto7.setElegido(false);
        presupuesto7.setOperacion(egreso6);
        presupuesto7.setProveedorP(CorralonSanJuan);
        presupuesto7.setOrganizacion(EAAF_BA);

        ItemPresupuesto itemp71 = new ItemPresupuesto();
        itemp71.setItemAsociado(item61);
        itemp71.setCantidad(10);
        itemp71.setTipo(PRODUCTO);
        itemp71.setValorUnitario(715.0);
        presupuesto7.agregarItem(itemp71);

        ItemPresupuesto itemp72 = new ItemPresupuesto();
        itemp72.setItemAsociado(item62);
        itemp72.setCantidad(5);
        itemp72.setTipo(PRODUCTO);
        itemp72.setValorUnitario(3150.0);
        presupuesto7.agregarItem(itemp72);

        ItemPresupuesto itemp73 = new ItemPresupuesto();
        itemp73.setItemAsociado(item63);
        itemp73.setCantidad(4);
        itemp73.setTipo(PRODUCTO);
        itemp73.setValorUnitario(880.0);
        presupuesto7.agregarItem(itemp73);

        ItemPresupuesto itemp74 = new ItemPresupuesto();
        itemp74.setItemAsociado(item64);
        itemp74.setCantidad(800);
        itemp74.setTipo(PRODUCTO);
        itemp74.setValorUnitario(235.0);
        presupuesto7.agregarItem(itemp74);

        egreso6.agregarPresupuesto(presupuesto7);

        repoEgresos.modificar(egreso6);

        //-------------------------------------Presupuesto8
        Presupuesto presupuesto8 = new Presupuesto();
        LocalDate fechap8 = LocalDate.of(2020, 9, 15);

        presupuesto8.setFecha(fechap8);
        presupuesto8.setElegido(true);
        presupuesto8.setOperacion(egreso6);
        presupuesto8.setProveedorP(CorralonLapridaSrl);
        presupuesto8.setOrganizacion(EAAF_BA);

        ItemPresupuesto itemp81 = new ItemPresupuesto();
        itemp81.setItemAsociado(item61);
        itemp81.setCantidad(10);
        itemp81.setTipo(PRODUCTO);
        itemp81.setValorUnitario(704.4);
        presupuesto8.agregarItem(itemp81);

        ItemPresupuesto itemp82 = new ItemPresupuesto();
        itemp82.setItemAsociado(item62);
        itemp82.setCantidad(5);
        itemp82.setTipo(PRODUCTO);
        itemp82.setValorUnitario(3100.0);
        presupuesto8.agregarItem(itemp82);

        ItemPresupuesto itemp83 = new ItemPresupuesto();
        itemp83.setItemAsociado(item63);
        itemp83.setCantidad(4);
        itemp83.setTipo(PRODUCTO);
        itemp83.setValorUnitario(891.0);
        presupuesto8.agregarItem(itemp83);

        ItemPresupuesto itemp84 = new ItemPresupuesto();
        itemp84.setItemAsociado(item64);
        itemp84.setCantidad(4);
        itemp84.setTipo(PRODUCTO);
        itemp84.setValorUnitario(227.0);
        presupuesto8.agregarItem(itemp84);
        presupuesto8.agregarItem(itemp84);

        egreso6.agregarPresupuesto(presupuesto8);

        repoEgresos.modificar(egreso6);

        //-------------------------------------Ingresos

        //-------------------------------------Ingreso1
        Ingreso ingreso1 = new Ingreso();
        LocalDate fecha_i1_0 = LocalDate.of(2020, 2, 25);
        LocalDate fecha_i1_f = LocalDate.of(2020, 3, 20);

        ingreso1.setFecha(fecha_i1_0);
        ingreso1.setFechaDeAceptabilidadEgreso(fecha_i1_f);

        ingreso1.setDescripcion("Donación de terceros");
        ingreso1.setValorTotal(20000.0);
        ingreso1.setOrganizacion(EAAF_BA);
        ingreso1.setMoneda(PesoArgentino);

        repoIngresos.agregar(ingreso1);


        //-----------------------------------Ingreso2
        Ingreso ingreso2 = new Ingreso();
        LocalDate fecha_i2_0 = LocalDate.of(2020, 5, 2);
        LocalDate fecha_i2_f = LocalDate.of(2020, 8, 3);

        ingreso2.setFecha(fecha_i2_0);
        ingreso2.setFechaDeAceptabilidadEgreso(fecha_i2_f);

        ingreso2.setDescripcion("Donación de Rimoli SA");
        ingreso2.setValorTotal(10000.0);
        ingreso2.setOrganizacion(EAAF_BA);
        ingreso2.setMoneda(PesoArgentino);

        repoIngresos.agregar(ingreso2);

        //----------------------------------Ingreso3
        Ingreso ingreso3 = new Ingreso();
        LocalDate fecha_i3_0 = LocalDate.of(2020, 8, 3);
        LocalDate fecha_i3_f = LocalDate.of(2020, 10, 1);

        ingreso3.setFecha(fecha_i3_0);
        ingreso3.setFechaDeAceptabilidadEgreso(fecha_i3_f);

        ingreso3.setDescripcion("Donación de Gran Imperio");
        ingreso3.setValorTotal(980000.0);
        ingreso3.setOrganizacion(EAAF_BA);
        ingreso3.setMoneda(PesoArgentino);

        repoIngresos.agregar(ingreso3);

        //-------------------------------Ingreso4
        Ingreso ingreso4 = new Ingreso();
        LocalDate fecha_i4_0 = LocalDate.of(2020, 5, 1);
        LocalDate fecha_i4_f = LocalDate.of(2020, 10, 1);

        ingreso4.setFecha(fecha_i4_0);
        ingreso4.setFechaDeAceptabilidadEgreso(fecha_i4_f);

        ingreso4.setDescripcion("Donación Gabino SRL");
        ingreso4.setValorTotal(10000.0);
        ingreso4.setOrganizacion(SurcosCs);
        ingreso4.setMoneda(PesoArgentino);

        repoIngresos.agregar(ingreso4);
    }
     */
}
