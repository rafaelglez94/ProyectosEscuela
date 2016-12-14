// Example program
#include <iostream>
#include <string>
#include <cstdlib>

#define length(x) (sizeof(x)/sizeof(x[0]))
using namespace std;

int main()
{
  int distritos[] ={1,2,3,4,5};


    string candidatos[]= {"Juan Montes","Pedro Zapata","Ana Martinez","Rogelio Guerra"};
    int votos[][5] = {{194,180,221,432,820},
                    {48,20,80,50,61},
                    {206,320,140,621,946},
                    {45,16,20,14,18}};
	
    int totalVotos=0;
    int votosCandidato[]={0,0,0,0};
    float porcentaje[4];
	int votosMaximos = 0;
	int segundoMasVotado;
	int masVotado;
    for(int i = 0 ; i < length(candidatos);i++){
        for(int j = 0 ; j < length(distritos);j++){
            votosCandidato[i]+= votos[i][j];
    		cout <<votosCandidato[i]<<" + "<<votos[i][j];
			cout <<endl;
        }
    	cout <<"Votos de "<<i<<" "<<votosCandidato[i];
		cout <<endl;
        if(votosMaximos < votosCandidato[i]){
        	votosMaximos = votosCandidato[i];
			segundoMasVotado=masVotado;
			masVotado = i;
		}
		totalVotos += votosCandidato[i];    	
    }
    for(int j = 0 ; j < length(votosCandidato);j++){
        porcentaje[j]= (float)( 100.0 / totalVotos   )*votosCandidato[j];
    }
    cout <<"Distritos\t|";
    for(int j = 0 ; j < length(distritos);j++){
        cout << distritos[j] <<"\t|";
    }
    cout <<"Total Votos\t|";
    cout <<"Porcentaje\t|";
    cout <<endl;
    
	for(int i = 0 ; i < length(candidatos);i++){
        cout << candidatos[i] <<"\t|";
        for(int j = 0 ; j < length(distritos);j++){
            cout << votos[i][j] <<"\t|";
        }
		if(votosCandidato[i]>10000){
	    	cout <<votosCandidato[i]<<"\t|";		
		}else{
	    	cout <<votosCandidato[i]<<"\t\t|";	
		}	
    	cout <<porcentaje[i]<<"\t|";
		cout <<endl;
    }
    if(porcentaje[masVotado]>50.0){
    	cout <<"El candidato "<<candidatos[masVotado] <<" es el ganador "<<endl;
   	}else{
 		cout <<"Seguiente Ronda"<<endl;
   		cout <<"El candidato "<<candidatos[masVotado] <<" con  "<<porcentaje[masVotado]<<"%"<<endl;
    	cout <<"El candidato "<<candidatos[segundoMasVotado] <<" con  "<<porcentaje[segundoMasVotado]<<"%"<<endl;
		
	}
    cout <<endl;
    system("pause");
    return EXIT_SUCCESS;
}

