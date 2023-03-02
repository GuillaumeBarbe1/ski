package net.ent.etrs.ski.model.entities.references;

public class ConstantesModel {
    public static final String PISTE_LONGUEUR_NULL = "La longueur de la piste doit être renseigné";
    public static final String PISTE_LONGUEUR_INCORRECTE = "La longueur de la piste ne peut pas être inférieur à zéro";
    public static final String PISTE_PENTE_MOYENNE_NULL = "La pente moyenne doit être renseigné";
    public static final String PISTE_PENTE_MOYENNE_INCORRECTE = "La pente moyenne de la piste ne peut pas être inférieur à zéro";
    public static final long PISTE_PENTE_MOYENNE_TROP_GRANDE = 100;
    public static final String PISTE_NOM_NULL = "Le nom de la piste doit être renseigné";
    public static final int PISTE_NOM_TAILLE_MIN = 3;
    public static final int PISTE_NOM_TAILLE_MAX = 50;
    public static final String PISTE_NOM_TAILLE_INCORRECTE = "Le nom de la piste doit contenir entre " + ConstantesModel.PISTE_NOM_TAILLE_MIN
            + " et " + ConstantesModel.PISTE_NOM_TAILLE_MAX + " caractères";
    public static final String PISTE_ETAT_NULL = "L'état de la piste doit être renseigné";
}
