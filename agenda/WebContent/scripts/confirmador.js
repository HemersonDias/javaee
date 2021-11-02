/**
 * Comfirmação de exclusao de um contato
 *
 * @author Emerson Dias 
 * @param idcon
 */

function confirmar(idcon){
	let resposta = confirm("Deseja excluir esse contato?")
	if(resposta === true){
		window.location.href = "delete?idcon=" + idcon
	}
}