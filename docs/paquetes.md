# Diagrama de paquetes (Mermaid)

El siguiente diagrama muestra las dependencias entre los paquetes principales del proyecto: `datos`, `negocios`, `uI`, `miPrincipal` y `miTest`.

```mermaid
flowchart TB
  subgraph DATOS [datos]
    DATOS_Producto[Producto]
    DATOS_Estudiante[Estudiante]
    DATOS_Prestamo[Prestamo]
    DATOS_UniversidadDAO[UniversidadDAO]
    DATOS_LectorArchivo[LectorArchivo]
  end

  subgraph NEGOCIOS [negocios]
    NEG_Universidad[Universidad]
    NEG_Service[UniversidadService]
    NEG_Criterio[Criterio]
  end

  subgraph UI [uI]
    UI_Aplicacion[Aplicacion]
    UI_Menu[Menu]
    UI_MenuProductos[MenuProductos]
    UI_MenuEstudiantes[MenuEstudiantes]
    UI_MenuPrestar[MenuPrestarDevolver]
    UI_MenuConsultar[MenuConsultar]
  end

  subgraph MP [miPrincipal]
    MP_Principal[Principal]
  end

  subgraph TEST [miTest]
    TEST_AppTest[AppTest]
  end

  %% Relaciones principales
  NEG_Service -->|usa| DATOS_UniversidadDAO
  NEG_Service -->|usa| NEG_Universidad
  UI_Menu -->|llama a| NEG_Service
  UI_Menu -->|a veces lee directamente| DATOS_UniversidadDAO
  MP_Principal -->|arranca| UI_Aplicacion
  TEST_AppTest -->|prueba lógica| NEG_Service
  TEST_AppTest -->|usa fixtures| DATOS_LectorArchivo

  %% relaciones de dominio
  NEG_Universidad --> DATOS_Prestamo
  NEG_Universidad --> DATOS_Producto
  DATOS_UniversidadDAO --> DATOS_Producto

  click DATOS_UniversidadDAO "./src/datos/UniversidadDAO.java" "Abrir UniversidadDAO"
  click NEG_Service "./src/negocios/UniversidadService.java" "Abrir UniversidadService"

  %% nota
  classDef note fill:#f9f,stroke:#333,stroke-width:1px
  class DATOS_UniversidadDAO note

```

Notas:
- Si abres este archivo en GitHub o en VS Code con una extensión de Mermaid, verás el diagrama renderizado.
- Los enlaces "click" apuntan a las rutas de los archivos en el repo y pueden funcionar en algunos visores (VS Code con extensiones).
