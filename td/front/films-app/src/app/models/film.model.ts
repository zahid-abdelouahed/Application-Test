import { Acteur } from "./acteur.model";

export type Genre =  'ACTION' | 'COMEDY' | 'DRAMA' | 'SCIENCE_FICTION' | 'CRIME' | 'FANTASY';
export const genres: Genre[] = ['ACTION', 'COMEDY', 'DRAMA', 'SCIENCE_FICTION', 'CRIME', 'FANTASY'];

export interface Film{
    id:number;
    titre:string;
    dateSortie:string | null;
    genre:Genre;
    realisateur:string;
    acteurs?: Acteur[];
}