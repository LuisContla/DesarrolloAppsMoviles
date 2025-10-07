package com.example.gameverse.data
import com.example.gameverse.R
import com.example.gameverse.data.models.*

object GameRepository {
    // ====== GÉNEROS ======
    val genres = listOf(
        Genre("g_action",     "Acción",      R.drawable.bg_arcade),
        Genre("g_rpg",        "RPG",         R.drawable.bg_rpg),
        Genre("g_platform",   "Plataformas", R.drawable.bg_platform),

        // NUEVOS (puedes cambiar el drawable cuando quieras)
        Genre("g_shooter",    "Shooter",     R.drawable.bg_arcade),
        Genre("g_strategy",   "Estrategia",  R.drawable.bg_rpg),
        Genre("g_racing",     "Carreras",    R.drawable.bg_platform),
        Genre("g_sports",     "Deportes",    R.drawable.bg_arcade),
        Genre("g_fighting",   "Lucha",       R.drawable.bg_rpg),
        Genre("g_adventure",  "Aventura",    R.drawable.bg_platform)
    )


    // ====== FRANQUICIAS ======
    val franchises = listOf(
        // RPG (ya tenías)
        Franchise("f_zelda",        "g_rpg",      "The Legend of Zelda", R.drawable.art_zelda),
        Franchise("f_finalfantasy", "g_rpg",      "Final Fantasy",       R.drawable.art_ff),

        // Acción (ya tenías Halo en acción)
        Franchise("f_halo",         "g_action",   "Halo",                R.drawable.art_halo),
        // Nuevas Acción
        Franchise("f_dmc",          "g_action",   "Devil May Cry",       R.drawable.art_halo),
        Franchise("f_gow",          "g_action",   "God of War",          R.drawable.art_halo),

        // Plataformas
        Franchise("f_mario",        "g_platform", "Super Mario",         R.drawable.art_ff),
        Franchise("f_sonic",        "g_platform", "Sonic",               R.drawable.art_zelda),

        // Shooter
        Franchise("f_cod",          "g_shooter",  "Call of Duty",        R.drawable.art_halo),
        Franchise("f_bf",           "g_shooter",  "Battlefield",         R.drawable.art_halo),

        // Estrategia
        Franchise("f_starcraft",    "g_strategy", "StarCraft",           R.drawable.art_ff),
        Franchise("f_aoe",          "g_strategy", "Age of Empires",      R.drawable.art_zelda),

        // Carreras
        Franchise("f_nfs",          "g_racing",   "Need for Speed",      R.drawable.art_ff),
        Franchise("f_forza",        "g_racing",   "Forza",               R.drawable.art_halo),
        Franchise("f_gt",           "g_racing",   "Gran Turismo",        R.drawable.art_zelda),

        // Deportes
        Franchise("f_fifa",         "g_sports",   "FIFA",                R.drawable.art_ff),
        Franchise("f_nba2k",        "g_sports",   "NBA 2K",              R.drawable.art_halo),

        // Lucha
        Franchise("f_sf",           "g_fighting", "Street Fighter",      R.drawable.art_zelda),
        Franchise("f_mk",           "g_fighting", "Mortal Kombat",       R.drawable.art_halo),
        Franchise("f_tekken",       "g_fighting", "Tekken",              R.drawable.art_ff),

        // Aventura
        Franchise("f_tombraider",   "g_adventure","Tomb Raider",         R.drawable.art_halo),
        Franchise("f_uncharted",    "g_adventure","Uncharted",           R.drawable.art_ff)
    )

    // ====== JUEGOS (1 por franquicia para que el click funcione) ======
    val games = listOf(
        // ——— RPG / Zelda ———
        Game(
            id = "game_botw",
            franchiseId = "f_zelda",
            title = "Breath of the Wild",
            coverRes = R.drawable.cover_botw,
            backdropRes = R.drawable.map_botw,
            year = 2017,
            developers = listOf("Nintendo EPD"),
            publisher = "Nintendo",
            platforms = listOf("Nintendo Switch", "Wii U"),
            description = "Zelda de mundo abierto con énfasis en exploración sistémica.",
            facts = listOf(
                "Ganó Juego del Año en The Game Awards 2017.",
                "Las físicas y la química del mundo permiten soluciones emergentes a santuarios y jefes."
            )
        ),
        Game(
            id = "game_totk",
            franchiseId = "f_zelda",
            title = "Tears of the Kingdom",
            coverRes = R.drawable.cover_halo3,
            backdropRes = R.drawable.map_botw,
            year = 2023,
            developers = listOf("Nintendo EPD"),
            publisher = "Nintendo",
            platforms = listOf("Nintendo Switch"),
            description = "Secuela que amplía construcción y verticalidad con nuevas habilidades.",
            facts = listOf(
                "Introduce Ultramano y Combinación para crear vehículos y armas.",
                "Añade islas celestes y profundidades como nuevos niveles del mundo."
            )
        ),
        Game(
            id = "game_alttp",
            franchiseId = "f_zelda",
            title = "A Link to the Past",
            coverRes = R.drawable.cover_botw,
            backdropRes = R.drawable.map_halo3,
            year = 1991,
            developers = listOf("Nintendo EAD"),
            publisher = "Nintendo",
            platforms = listOf("SNES", "GBA", "Nintendo Switch Online"),
            description = "Clásico top-down que definió convenciones de la saga.",
            facts = listOf(
                "Popularizó el concepto de Mundo Oscuro/Luz con puzzles entre capas.",
                "Inspiró directamente a múltiples Zeldas posteriores."
            )
        ),

        // ——— RPG / Final Fantasy ———
        Game(
            id = "game_ff7",
            franchiseId = "f_finalfantasy",
            title = "Final Fantasy VII",
            coverRes = R.drawable.cover_botw,
            backdropRes = R.drawable.map_botw,
            year = 1997,
            developers = listOf("Square"),
            publisher = "Square",
            platforms = listOf("PS1", "PC", "Switch", "PS4/PS5 (ports)"),
            description = "JRPG icónico con Materia y gran impacto en occidente.",
            facts = listOf(
                "Pionero en el uso extensivo de cinemáticas CG en consolas.",
                "Ayudó a consolidar el éxito de la primera PlayStation."
            )
        ),
        Game(
            id = "game_ffx",
            franchiseId = "f_finalfantasy",
            title = "Final Fantasy X",
            coverRes = R.drawable.cover_halo3,
            backdropRes = R.drawable.map_halo3,
            year = 2001,
            developers = listOf("Square"),
            publisher = "Square",
            platforms = listOf("PS2", "PS3/PS4 (HD Remaster)", "Switch"),
            description = "Introduce voces completas y el sistema CTB.",
            facts = listOf(
                "Primer FF principal con actuación de voz.",
                "El CTB muestra el orden exacto de acciones."
            )
        ),
        Game(
            id = "game_ff15",
            franchiseId = "f_finalfantasy",
            title = "Final Fantasy XV",
            coverRes = R.drawable.cover_botw,
            backdropRes = R.drawable.map_botw,
            year = 2016,
            developers = listOf("Square Enix Business Division 2"),
            publisher = "Square Enix",
            platforms = listOf("PS4", "Xbox One", "PC"),
            description = "Mundo abierto con combate en tiempo real.",
            facts = emptyList()
        ),

        // ——— Acción / Halo ———
        Game(
            id = "game_halo3",
            franchiseId = "f_halo",
            title = "Halo 3",
            coverRes = R.drawable.cover_halo3,
            backdropRes = R.drawable.map_halo3,
            year = 2007,
            developers = listOf("Bungie"),
            publisher = "Microsoft Game Studios",
            platforms = listOf("Xbox 360", "Xbox One (MCC)", "PC (MCC)"),
            description = "Cierre de trilogía con campaña coop y Forge.",
            facts = listOf(
                "Introdujo el modo Forge y el Teatro para compartir partidas y clips.",
                "Fenómeno de Xbox Live con multijugador masivo."
            )
        ),
        Game(
            id = "game_haloce",
            franchiseId = "f_halo",
            title = "Halo: Combat Evolved",
            coverRes = R.drawable.cover_botw,
            backdropRes = R.drawable.map_botw,
            year = 2001,
            developers = listOf("Bungie"),
            publisher = "Microsoft Game Studios",
            platforms = listOf("Xbox", "PC", "Xbox One (MCC)"),
            description = "Inicio del Jefe Maestro; base del shooter moderno.",
            facts = listOf(
                "Título de lanzamiento clave para Xbox.",
                "Popularizó LAN parties en consola con System Link."
            )
        ),
        Game(
            id = "game_halor",
            franchiseId = "f_halo",
            title = "Halo: Reach",
            coverRes = R.drawable.cover_halo3,
            backdropRes = R.drawable.map_halo3,
            year = 2010,
            developers = listOf("Bungie"),
            publisher = "Microsoft Game Studios",
            platforms = listOf("Xbox 360", "Xbox One (MCC)", "PC (MCC)"),
            description = "Precuela centrada en el Equipo Noble.",
            facts = listOf(
                "Último Halo desarrollado por Bungie.",
                "Introdujo Habilidades de Armadura como sprint y jetpack."
            )
        ),

        // ——— Acción / Devil May Cry ———
        Game(
            id = "game_dmc5",
            franchiseId = "f_dmc",
            title = "Devil May Cry 5",
            coverRes = R.drawable.cover_halo3,
            backdropRes = R.drawable.map_halo3,
            year = 2019,
            developers = listOf("Capcom"),
            publisher = "Capcom",
            platforms = listOf("PS4", "Xbox One", "PC", "PS5/XSX (SE)"),
            description = "Acción estilosa con Dante, Nero y V.",
            facts = listOf(
                "Usa RE Engine (de RE7/RE2 Remake).",
                "V combate con familiares: Griffon, Shadow y Nightmare."
            )
        ),
        Game(
            id = "game_dmc3",
            franchiseId = "f_dmc",
            title = "Devil May Cry 3",
            coverRes = R.drawable.cover_botw,
            backdropRes = R.drawable.map_botw,
            year = 2005,
            developers = listOf("Capcom"),
            publisher = "Capcom",
            platforms = listOf("PS2", "PC", "Switch (port)"),
            description = "Pulió el sistema de estilos y la dificultad.",
            facts = emptyList()
        ),

        // ——— Acción / God of War ———
        Game(
            id = "game_gow2018",
            franchiseId = "f_gow",
            title = "God of War (2018)",
            coverRes = R.drawable.cover_botw,
            backdropRes = R.drawable.map_botw,
            year = 2018,
            developers = listOf("Santa Monica Studio"),
            publisher = "Sony Interactive Entertainment",
            platforms = listOf("PS4", "PC"),
            description = "Reinvención nórdica con plano secuencia.",
            facts = listOf(
                "Toda la cámara es un ‘plano secuencia’ sin cortes visibles.",
                "El hacha Leviatán vuelve a la mano y habilita puzzles-combate."
            )
        ),
        Game(
            id = "game_gowrag",
            franchiseId = "f_gow",
            title = "God of War Ragnarök",
            coverRes = R.drawable.cover_halo3,
            backdropRes = R.drawable.map_halo3,
            year = 2022,
            developers = listOf("Santa Monica Studio"),
            publisher = "Sony Interactive Entertainment",
            platforms = listOf("PS5", "PS4"),
            description = "Conclusión de la saga nórdica.",
            facts = emptyList()
        ),
        Game(
            id = "game_gow2",
            franchiseId = "f_gow",
            title = "God of War II",
            coverRes = R.drawable.cover_botw,
            backdropRes = R.drawable.map_botw,
            year = 2007,
            developers = listOf("Santa Monica Studio"),
            publisher = "Sony Computer Entertainment",
            platforms = listOf("PS2", "PS3 (HD Collection)"),
            description = "Secuela original con jefes espectaculares.",
            facts = emptyList()
        ),

        // ——— Plataformas / Mario ———
        Game(
            id = "game_mario_od",
            franchiseId = "f_mario",
            title = "Super Mario Odyssey",
            coverRes = R.drawable.cover_botw,
            backdropRes = R.drawable.map_botw,
            year = 2017,
            developers = listOf("Nintendo EPD"),
            publisher = "Nintendo",
            platforms = listOf("Nintendo Switch"),
            description = "Sandbox con Cappy y reinos abiertos.",
            facts = listOf(
                "Permite ‘capturar’ enemigos/objetos para ganar habilidades.",
                "Incluye el musical ‘Jump Up, Super Star!’ en Nueva Donk City."
            )
        ),
        Game(
            id = "game_mario_gx",
            franchiseId = "f_mario",
            title = "Super Mario Galaxy",
            coverRes = R.drawable.cover_halo3,
            backdropRes = R.drawable.map_halo3,
            year = 2007,
            developers = listOf("Nintendo EAD Tokyo"),
            publisher = "Nintendo",
            platforms = listOf("Wii", "Switch (3D All-Stars)"),
            description = "Aventura espacial con gravedad esférica.",
            facts = listOf(
                "Popularizó plataformas sobre planetas con gravedad local.",
                "Banda sonora orquestada muy aclamada."
            )
        ),
        Game(
            id = "game_mario64",
            franchiseId = "f_mario",
            title = "Super Mario 64",
            coverRes = R.drawable.cover_botw,
            backdropRes = R.drawable.map_botw,
            year = 1996,
            developers = listOf("Nintendo EAD"),
            publisher = "Nintendo",
            platforms = listOf("Nintendo 64", "Switch (3D All-Stars)"),
            description = "Pionero del 3D con libertad de movimiento.",
            facts = emptyList()
        ),

        // ——— Plataformas / Sonic ———
        Game(
            id = "game_sonic_m",
            franchiseId = "f_sonic",
            title = "Sonic Mania",
            coverRes = R.drawable.cover_halo3,
            backdropRes = R.drawable.map_halo3,
            year = 2017,
            developers = listOf("Christian Whitehead", "Headcannon", "PagodaWest Games"),
            publisher = "Sega",
            platforms = listOf("PS4", "Xbox One", "Switch", "PC"),
            description = "Homenaje retro con nuevas zonas y físicas clásicas.",
            facts = listOf(
                "Proyecto liderado por la comunidad con aval oficial de Sega.",
                "Mezcla zonas clásicas reimaginadas con contenido nuevo."
            )
        ),
        Game(
            id = "game_sonic_g",
            franchiseId = "f_sonic",
            title = "Sonic Generations",
            coverRes = R.drawable.cover_botw,
            backdropRes = R.drawable.map_botw,
            year = 2011,
            developers = listOf("Sonic Team"),
            publisher = "Sega",
            platforms = listOf("PS3", "Xbox 360", "PC"),
            description = "Combina Sonic clásico y moderno.",
            facts = emptyList()
        ),

        // ——— Shooter / Call of Duty ———
        Game(
            id = "game_cod_mw",
            franchiseId = "f_cod",
            title = "Call of Duty: Modern Warfare",
            coverRes = R.drawable.cover_halo3,
            backdropRes = R.drawable.map_halo3,
            year = 2007,
            developers = listOf("Infinity Ward"),
            publisher = "Activision",
            platforms = listOf("PC", "PS3", "Xbox 360"),
            description = "FPS moderno con campaña cinematográfica y progresión online.",
            facts = listOf(
                "Misiones icónicas como ‘All Ghillied Up’.",
                "Estableció perks, rachas y clases personalizadas."
            )
        ),
        Game(
            id = "game_cod_bo",
            franchiseId = "f_cod",
            title = "Call of Duty: Black Ops",
            coverRes = R.drawable.cover_botw,
            backdropRes = R.drawable.map_botw,
            year = 2010,
            developers = listOf("Treyarch"),
            publisher = "Activision",
            platforms = listOf("PC", "PS3", "Xbox 360", "Wii"),
            description = "Guerra fría, Zombies y multijugador popular.",
            facts = emptyList()
        ),
        Game(
            id = "game_cod_mw2",
            franchiseId = "f_cod",
            title = "Call of Duty: MW2 (2009)",
            coverRes = R.drawable.cover_halo3,
            backdropRes = R.drawable.map_halo3,
            year = 2009,
            developers = listOf("Infinity Ward"),
            publisher = "Activision",
            platforms = listOf("PC", "PS3", "Xbox 360"),
            description = "Secuela que elevó el multijugador.",
            facts = listOf(
                "Incluye la misión polémica ‘No Russian’.",
                "Estrenó el modo cooperativo ‘Spec Ops’."
            )
        ),

        // ——— Shooter / Battlefield ———
        Game(
            id = "game_bf1",
            franchiseId = "f_bf",
            title = "Battlefield 1",
            coverRes = R.drawable.cover_botw,
            backdropRes = R.drawable.map_botw,
            year = 2016,
            developers = listOf("DICE"),
            publisher = "Electronic Arts",
            platforms = listOf("PC", "PS4", "Xbox One"),
            description = "Primera Guerra Mundial a gran escala.",
            facts = listOf(
                "Campaña en ‘Historias de Guerra’ antológicas.",
                "Behemoths (dirigible, tren, acorazado) como eventos de mapa."
            )
        ),
        Game(
            id = "game_bf3",
            franchiseId = "f_bf",
            title = "Battlefield 3",
            coverRes = R.drawable.cover_halo3,
            backdropRes = R.drawable.map_halo3,
            year = 2011,
            developers = listOf("DICE"),
            publisher = "Electronic Arts",
            platforms = listOf("PC", "PS3", "Xbox 360"),
            description = "Frostbite 2 con destrucción y multijugador competitivo.",
            facts = emptyList()
        ),

        // ——— Estrategia / StarCraft ———
        Game(
            id = "game_sc2",
            franchiseId = "f_starcraft",
            title = "StarCraft II",
            coverRes = R.drawable.cover_botw,
            backdropRes = R.drawable.map_botw,
            year = 2010,
            developers = listOf("Blizzard Entertainment"),
            publisher = "Blizzard Entertainment",
            platforms = listOf("PC"),
            description = "RTS con tres campañas y esports.",
            facts = listOf(
                "Se publicó en Wings of Liberty, Heart of the Swarm y Legacy of the Void.",
                "El multijugador pasó a free-to-play años después."
            )
        ),
        Game(
            id = "game_sc_bw",
            franchiseId = "f_starcraft",
            title = "StarCraft: Brood War",
            coverRes = R.drawable.cover_halo3,
            backdropRes = R.drawable.map_halo3,
            year = 1998,
            developers = listOf("Blizzard Entertainment"),
            publisher = "Blizzard Entertainment",
            platforms = listOf("PC"),
            description = "Expansión legendaria del StarCraft original.",
            facts = listOf(
                "Piedra angular del esports en Corea del Sur.",
                "Añadió unidades clave que definieron el meta por años."
            )
        ),

        // ——— Estrategia / Age of Empires ———
        Game(
            id = "game_aoe2",
            franchiseId = "f_aoe",
            title = "Age of Empires II",
            coverRes = R.drawable.cover_halo3,
            backdropRes = R.drawable.map_halo3,
            year = 1999,
            developers = listOf("Ensemble Studios"),
            publisher = "Microsoft",
            platforms = listOf("PC", "Xbox (DE)"),
            description = "RTS medieval centrado en civilizaciones.",
            facts = listOf(
                "La Edición Definitiva reavivó la escena competitiva décadas después.",
                "Conocido por sus campañas históricas y equilibrio de civilizaciones."
            )
        ),
        Game(
            id = "game_aoe4",
            franchiseId = "f_aoe",
            title = "Age of Empires IV",
            coverRes = R.drawable.cover_botw,
            backdropRes = R.drawable.map_botw,
            year = 2021,
            developers = listOf("Relic Entertainment", "World’s Edge"),
            publisher = "Xbox Game Studios",
            platforms = listOf("PC", "Xbox Series"),
            description = "Regreso moderno con campañas históricas y facciones asimétricas.",
            facts = emptyList()
        ),

        // ——— Carreras / NFS ———
        Game(
            id = "game_nfs_heat",
            franchiseId = "f_nfs",
            title = "Need for Speed Heat",
            coverRes = R.drawable.cover_halo3,
            backdropRes = R.drawable.map_halo3,
            year = 2019,
            developers = listOf("Ghost Games"),
            publisher = "Electronic Arts",
            platforms = listOf("PC", "PS4", "Xbox One"),
            description = "Mundo abierto urbano con ciclo día/noche y tuning.",
            facts = emptyList()
        ),
        Game(
            id = "game_nfs_mw",
            franchiseId = "f_nfs",
            title = "Need for Speed Most Wanted (2005)",
            coverRes = R.drawable.cover_botw,
            backdropRes = R.drawable.map_botw,
            year = 2005,
            developers = listOf("EA Black Box"),
            publisher = "Electronic Arts",
            platforms = listOf("PS2", "Xbox", "PC", "GameCube"),
            description = "Persecuciones policiales y lista negra de rivales.",
            facts = emptyList()
        ),

        // ——— Carreras / Forza ———
        Game(
            id = "game_forza_h4",
            franchiseId = "f_forza",
            title = "Forza Horizon 4",
            coverRes = R.drawable.cover_botw,
            backdropRes = R.drawable.map_botw,
            year = 2018,
            developers = listOf("Playground Games"),
            publisher = "Xbox Game Studios",
            platforms = listOf("PC", "Xbox One"),
            description = "Mundo abierto en Reino Unido con estaciones dinámicas.",
            facts = emptyList()
        ),
        Game(
            id = "game_forza_h5",
            franchiseId = "f_forza",
            title = "Forza Horizon 5",
            coverRes = R.drawable.cover_halo3,
            backdropRes = R.drawable.map_halo3,
            year = 2021,
            developers = listOf("Playground Games"),
            publisher = "Xbox Game Studios",
            platforms = listOf("PC", "Xbox Series", "Xbox One"),
            description = "Ambientado en México con biomas variados.",
            facts = listOf(
                "Incluye ‘EventLab’ para crear circuitos y modos personalizados.",
                "Mapa más grande de la saga Horizon hasta su salida."
            )
        ),

        // ——— Carreras / Gran Turismo ———
        Game(
            id = "game_gt7",
            franchiseId = "f_gt",
            title = "Gran Turismo 7",
            coverRes = R.drawable.cover_halo3,
            backdropRes = R.drawable.map_halo3,
            year = 2022,
            developers = listOf("Polyphony Digital"),
            publisher = "Sony Interactive Entertainment",
            platforms = listOf("PS5", "PS4"),
            description = "Sim con énfasis en colección y cultura del automóvil.",
            facts = listOf(
                "El Café guía el progreso con ‘Menu Books’.",
                "‘Scapes’ permite sesiones fotográficas en localizaciones reales."
            )
        ),
        Game(
            id = "game_gts",
            franchiseId = "f_gt",
            title = "Gran Turismo Sport",
            coverRes = R.drawable.cover_botw,
            backdropRes = R.drawable.map_botw,
            year = 2017,
            developers = listOf("Polyphony Digital"),
            publisher = "Sony Interactive Entertainment",
            platforms = listOf("PS4"),
            description = "Centrado en competición online y licencias FIA.",
            facts = emptyList()
        ),

        // ——— Deportes / FIFA ———
        Game(
            id = "game_fifa23",
            franchiseId = "f_fifa",
            title = "FIFA 23",
            coverRes = R.drawable.cover_botw,
            backdropRes = R.drawable.map_botw,
            year = 2022,
            developers = listOf("EA Vancouver", "EA Romania"),
            publisher = "Electronic Arts",
            platforms = listOf("PC", "PS4", "PS5", "Xbox One", "Xbox Series", "Switch (Legacy)"),
            description = "Último FIFA antes del cambio a EA Sports FC.",
            facts = listOf(
                "Agregó clubes femeniles y avances en Hypermotion 2.",
                "Incluyó contenido de las Copas del Mundo masculina y femenina."
            )
        ),
        Game(
            id = "game_fifa21",
            franchiseId = "f_fifa",
            title = "FIFA 21",
            coverRes = R.drawable.cover_halo3,
            backdropRes = R.drawable.map_halo3,
            year = 2020,
            developers = listOf("EA Vancouver", "EA Romania"),
            publisher = "Electronic Arts",
            platforms = listOf("PC", "PS4", "PS5", "Xbox One", "Xbox Series", "Switch (Legacy)"),
            description = "Entrega anual con mejoras en VOLTA y modos online.",
            facts = emptyList()
        ),

        // ——— Deportes / NBA 2K ———
        Game(
            id = "game_nba2k24",
            franchiseId = "f_nba2k",
            title = "NBA 2K24",
            coverRes = R.drawable.cover_halo3,
            backdropRes = R.drawable.map_halo3,
            year = 2023,
            developers = listOf("Visual Concepts"),
            publisher = "2K",
            platforms = listOf("PC", "PS4", "PS5", "Xbox One", "Xbox Series", "Switch"),
            description = "Sim de baloncesto con modos MyCareer y MyTeam.",
            facts = listOf(
                "Incluye ‘Mamba Moments’ en homenaje a Kobe Bryant.",
                "En nueva generación usa ProPLAY para trasladar animaciones de material real."
            )
        ),
        Game(
            id = "game_nba2k20",
            franchiseId = "f_nba2k",
            title = "NBA 2K20",
            coverRes = R.drawable.cover_botw,
            backdropRes = R.drawable.map_botw,
            year = 2019,
            developers = listOf("Visual Concepts"),
            publisher = "2K",
            platforms = listOf("PC", "PS4", "Xbox One", "Switch"),
            description = "Entrega 2019/20 con mejoras en físicas y presentación.",
            facts = emptyList()
        ),

        // ——— Lucha / Street Fighter ———
        Game(
            id = "game_sf6",
            franchiseId = "f_sf",
            title = "Street Fighter 6",
            coverRes = R.drawable.cover_halo3,
            backdropRes = R.drawable.map_halo3,
            year = 2023,
            developers = listOf("Capcom"),
            publisher = "Capcom",
            platforms = listOf("PC", "PS4", "PS5", "Xbox Series"),
            description = "Controles modernos y modo World Tour.",
            facts = listOf(
                "El sistema ‘Drive’ unifica mecánicas ofensivas y defensivas.",
                "World Tour permite crear tu avatar y explorar zonas abiertas."
            )
        ),
        Game(
            id = "game_sf5",
            franchiseId = "f_sf",
            title = "Street Fighter V",
            coverRes = R.drawable.cover_botw,
            backdropRes = R.drawable.map_botw,
            year = 2016,
            developers = listOf("Capcom"),
            publisher = "Capcom",
            platforms = listOf("PS4", "PC"),
            description = "Estructura por temporadas, centrado en el competitivo.",
            facts = emptyList()
        ),

        // ——— Lucha / Mortal Kombat ———
        Game(
            id = "game_mk11",
            franchiseId = "f_mk",
            title = "Mortal Kombat 11",
            coverRes = R.drawable.cover_botw,
            backdropRes = R.drawable.map_botw,
            year = 2019,
            developers = listOf("NetherRealm Studios"),
            publisher = "Warner Bros. Interactive Entertainment",
            platforms = listOf("PC", "PS4", "PS5", "Xbox One", "Xbox Series", "Switch"),
            description = "Variaciones personalizadas y fatalities espectaculares.",
            facts = listOf(
                "Introduce los ‘Fatal Blows’ como remate bajo vida.",
                "Historia de viajes temporales con la villana Kronika."
            )
        ),
        Game(
            id = "game_mkx",
            franchiseId = "f_mk",
            title = "Mortal Kombat X",
            coverRes = R.drawable.cover_halo3,
            backdropRes = R.drawable.map_halo3,
            year = 2015,
            developers = listOf("NetherRealm Studios"),
            publisher = "Warner Bros. Interactive Entertainment",
            platforms = listOf("PC", "PS4", "Xbox One"),
            description = "Introdujo estilos por personaje y elenco renovado.",
            facts = emptyList()
        ),

        // ——— Lucha / Tekken ———
        Game(
            id = "game_tekken7",
            franchiseId = "f_tekken",
            title = "Tekken 7",
            coverRes = R.drawable.cover_halo3,
            backdropRes = R.drawable.map_halo3,
            year = 2017,
            developers = listOf("Bandai Namco Studios"),
            publisher = "Bandai Namco Entertainment",
            platforms = listOf("PC", "PS4", "Xbox One"),
            description = "Rage Arts y conclusión del conflicto Mishima.",
            facts = listOf(
                "Incluye a Akuma (Street Fighter) como invitado con jugabilidad fiel.",
                "El sistema ‘Screw’ sustituyó los bounds de Tekken 6."
            )
        ),
        Game(
            id = "game_tekken8",
            franchiseId = "f_tekken",
            title = "Tekken 8",
            coverRes = R.drawable.cover_botw,
            backdropRes = R.drawable.map_botw,
            year = 2024,
            developers = listOf("Bandai Namco Studios"),
            publisher = "Bandai Namco Entertainment",
            platforms = listOf("PC", "PS5", "Xbox Series"),
            description = "Sistema Heat y mejoras visuales en Unreal Engine 5.",
            facts = emptyList()
        ),

        // ——— Aventura / Tomb Raider ———
        Game(
            id = "game_tr2013",
            franchiseId = "f_tombraider",
            title = "Tomb Raider (2013)",
            coverRes = R.drawable.cover_botw,
            backdropRes = R.drawable.map_botw,
            year = 2013,
            developers = listOf("Crystal Dynamics"),
            publisher = "Square Enix",
            platforms = listOf("PC", "PS3/PS4", "Xbox 360/One"),
            description = "Reinicio con supervivencia y arco de origen de Lara.",
            facts = listOf(
                "Enfatiza el arco y el crafteo como pilares de gameplay.",
                "Inició la reinterpretación más cinemática de la franquicia."
            )
        ),
        Game(
            id = "game_tr_rise",
            franchiseId = "f_tombraider",
            title = "Rise of the Tomb Raider",
            coverRes = R.drawable.cover_halo3,
            backdropRes = R.drawable.map_halo3,
            year = 2015,
            developers = listOf("Crystal Dynamics"),
            publisher = "Square Enix",
            platforms = listOf("Xbox One", "PC", "PS4"),
            description = "Expande la exploración con hubs nevados y criptas.",
            facts = emptyList()
        ),

        // ——— Aventura / Uncharted ———
        Game(
            id = "game_unch4",
            franchiseId = "f_uncharted",
            title = "Uncharted 4: A Thief’s End",
            coverRes = R.drawable.cover_halo3,
            backdropRes = R.drawable.map_halo3,
            year = 2016,
            developers = listOf("Naughty Dog"),
            publisher = "Sony Interactive Entertainment",
            platforms = listOf("PS4", "PS5/PC (Legacy of Thieves)"),
            description = "Cierre de la saga de Nathan Drake.",
            facts = listOf(
                "Set pieces memorables: persecución en Madagascar y uso del gancho.",
                "Gran despliegue facial y narrativo; enfoque en la familia Drake."
            )
        ),
        Game(
            id = "game_unch2",
            franchiseId = "f_uncharted",
            title = "Uncharted 2: Among Thieves",
            coverRes = R.drawable.cover_botw,
            backdropRes = R.drawable.map_botw,
            year = 2009,
            developers = listOf("Naughty Dog"),
            publisher = "Sony Computer Entertainment",
            platforms = listOf("PS3", "PS4 (Nathan Drake Collection)"),
            description = "Aventura cinematográfica con ritmo y variedad de escenarios.",
            facts = emptyList()
        )
    )


    fun franchisesByGenre(genreId: String) = franchises.filter { it.genreId == genreId }
    fun gamesByFranchise(franchiseId: String) = games.filter { it.franchiseId == franchiseId }

    val pois = listOf(
        Poi("p1","game_botw","Arma legendaria","Espada oculta en el bosque.", R.drawable.poi_sword, 0.22f,0.65f),
        Poi("p2","game_botw","Jefe secreto","Derrota opcional con gran botín.", R.drawable.poi_boss, 0.71f,0.38f),
        Poi("p3","game_botw","Zona oculta","Cueva con ítems raros.", R.drawable.poi_cave, 0.48f,0.82f)
    )
    fun poisByGame(gameId: String) = pois.filter { it.gameId == gameId }
}
