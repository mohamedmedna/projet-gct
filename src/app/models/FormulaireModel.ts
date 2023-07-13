export interface FormulaireModel {
  id: number;
  nom: string;
  numConsultation: string;
  numConsultationestVisible: boolean;
  titreConsultation: string;
  titreConsultationestVisible: boolean;
  objetConsultation: string;
  objetConsultationestVisible: boolean;
  conditionsParticipation: string;
  conditionsParticipationestVisible: boolean;
  delaiLivraison: Date;
  delaiLivraisonestVisible: boolean;
  dureeGarantie: Date;
  dureeGarantieestVisible: boolean;
  modifier: boolean;
}
