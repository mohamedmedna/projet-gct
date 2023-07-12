export interface FormulaireModel {
  id: number;
  nom: string;
  champs: ChampModel[];
}

export interface ChampModel {
  nom: string;
  visible: boolean;
}
