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
  delaiLivraison: string;
  delaiLivraisonestVisible: boolean;
  dureeGarantie: string;
  dureeGarantieestVisible: boolean;
  modifier: boolean;
}
