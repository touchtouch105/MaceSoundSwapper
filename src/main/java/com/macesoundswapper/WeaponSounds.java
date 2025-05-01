package com.macesoundswapper;

public enum WeaponSounds {
        DEFAULT,
        WARHAMMER,
        GODSWORD_CRUSH,
        DH_AXE_CRUSH,
        DH_AXE_SLASH,
        ELDER_MAUL;

        private final int MACE_CRUSH_SOUNDID = 2508;
        private final int DH_AXE_CRUSH_SOUNDID = 1316;
        private final int DH_AXE_SLASH_SOUNDID = 1321;
        private final int WARHAMMER_SOUNDID = 2567;
        private final int ELDER_MAUL_SOUNDID = 3454;
        private final int GODSWORD_CRUSH_SOUNDID = 3846;

        public int getSoundID()
        {
            WeaponSounds soundChoice = this;
            switch ( soundChoice )
            {
                case DH_AXE_CRUSH:
                    return DH_AXE_CRUSH_SOUNDID;
                case DH_AXE_SLASH:
                    return DH_AXE_SLASH_SOUNDID;
                case WARHAMMER:
                    return WARHAMMER_SOUNDID;
                case ELDER_MAUL:
                    return ELDER_MAUL_SOUNDID;
                case GODSWORD_CRUSH:
                    return GODSWORD_CRUSH_SOUNDID;
                case DEFAULT:
                default:
                    return MACE_CRUSH_SOUNDID;
            }
        }
}
