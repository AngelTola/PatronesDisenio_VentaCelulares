// ==========================================
// 1. PATRON CREACIONAL: FACTORY METHOD
// ==========================================
interface Celular {
    String getDescripcion();
    double getPrecio();
}

class CelularBasico implements Celular {
    @Override
    public String getDescripcion(){
        return "Nokia Basico";
    }
    @Override
    public double getPrecio(){
        return 200.0; 
    }
}

class Smartphone implements Celular {
    @Override
    public String getDescripcion(){
        return "Samsung Galaxy S24";
    }
    @Override
    public double getPrecio(){
        return 5000.0; 
    }
}

// Creador Abstracto 
interface CelularFactory {
    Celular crearCelular();
}

// Creadores Concretos 
class SmartphoneFactory implements CelularFactory {
    @Override
    public Celular crearCelular(){
        return new Smartphone();
    }
}

class BasicoFactory implements CelularFactory {
    @Override
    public Celular crearCelular(){
        return new CelularBasico();
    }
}

// ==========================================
// 2. PATRON ESTRUCTURAL: DECORATOR
// ==========================================
abstract class CelularDecorator implements Celular {
    protected Celular celularWrapper;

    public CelularDecorator(Celular celular){
        this.celularWrapper = celular;
    }
    
    @Override
    public String getDescripcion(){
        return celularWrapper.getDescripcion();
    }
    
    @Override
    public double getPrecio(){
        return celularWrapper.getPrecio();
    }
}

class FundaDecorator extends CelularDecorator {
    public FundaDecorator(Celular celular){
        super(celular);
    }
    @Override
    public String getDescripcion(){
        return super.getDescripcion()+ " + Funda Protectora";
    }
    @Override
    public double getPrecio(){
        return super.getPrecio()+ 50.0;
    }
}

// ==========================================
// 3. PATRON DE COMPORTAMIENTO: STRATEGY
// ==========================================
interface EstrategiaPago {
    void procesarPago(double monto);
}

class PagoEfectivo implements EstrategiaPago {
    @Override
    public void procesarPago(double monto){
        System.out.println("-> Pagando Bs " + monto + " en Efectivo");
    }
}

class PagoQR implements EstrategiaPago {
    @Override
    public void procesarPago(double monto){
        System.out.println("-> Pagando Bs " + monto + " mediante codigo QR");
    }
}

// ==========================================
// CLASE VENTA donde se usa los patrones
// ==========================================
class Venta {
    private Celular celular;
    private EstrategiaPago estrategiaPago;

    public Venta(Celular celular, EstrategiaPago estrategiaPago){
        this.celular = celular;
        this.estrategiaPago = estrategiaPago;
    }

    public void confirmarVenta(){
        System.out.println("Producto: " + celular.getDescripcion());
        System.out.println("Total a cobrar: Bs " + celular.getPrecio());
        estrategiaPago.procesarPago(celular.getPrecio());
        System.out.println("-------------------------------------------\n");
    }
}

// ==========================================
// CLASE MAIN
// ==========================================
public class Main {
    public static void main(String[] args){
        System.out.println("=== SISTEMA DE VENTAS CON PATRONES DE DISENIO ===\n");

        // PATRON 1: FACTORY METHOD 
        CelularFactory smartFactory = new SmartphoneFactory();
        Celular miCelular = smartFactory.crearCelular();
        
        // PATRON 2: DECORATOR 
        miCelular = new FundaDecorator(miCelular);
        
        // PATRON 3: STRATEGY 
        EstrategiaPago pagoQR = new PagoQR();
        
        System.out.println("--- VENTA 1: Smartphone con funda pagado por QR ---");
        Venta venta1 = new Venta(miCelular, pagoQR);
        venta1.confirmarVenta();
        
        System.out.println("--- VENTA 2: Celular basico sin accesorios pagado en Efectivo ---");
        CelularFactory basicoFactory = new BasicoFactory();
        Celular nokia = basicoFactory.crearCelular();
        
        Venta venta2 = new Venta(nokia, new PagoEfectivo());
        venta2.confirmarVenta();
    }
}