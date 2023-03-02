package net.ent.etrs.ski.utils;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ResourceBundle;

/**
 * Outillage générique JSF.
 *
 * @author CDT RBN
 */
public final class JsfUtils
{

	private JsfUtils()
	{
		// protection du constructeur
	}

	/**
	 * conserve les message dans le FlashScope pour un composant Growl en
	 * faces-redirect.
	 * 
	 */
	public static void keepMessages()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
	}

	/**
	 * Envoie un message JSF pour l'IHM.
	 * 
	 * @param severity
	 *            niveau de sévérité.
	 * @param stringFormat
	 *            chaine de formatage (genre String.format()).
	 * @param objects
	 *            objets à insérer dans la chaine de formatage.
	 */
	public static void sendMessage(Severity severity, String stringFormat, Object... objects)
	{
		keepMessages();
		String message = String.format(stringFormat, objects);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, message));
	}

	public static void sendMessage(String message)
	{
		keepMessages();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
	}

	/**
	 * Envoie le message d'une exception à l'IHM. Classe : ERROR
	 *
	 * @param ex
	 */
	public static void sendMessage(Exception ex)
	{
		keepMessages();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
	}

	/**
	 * Envoie le message d'une exception à l'IHM à un composant en particulier.
	 * Classe : ERROR
	 *
	 * @param componentId
	 *            identifiant JSF du composant destinataire du message.
	 * @param ex
	 *            exception à envoyer.
	 */
	public static void sendMessage(String componentId, Exception ex)
	{
		keepMessages();
		FacesContext.getCurrentInstance().addMessage(componentId, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
	}

	/**
	 * Envoie un message de classe INFO à un composant JSF identifié par son
	 * "id".
	 *
	 * @param composantId
	 *            identifiant JSF du composant destinataire du message.
	 * @param message
	 *            message à afficher.
	 */
	public static void sendMessage(String composantId, String message)
	{
		keepMessages();
		FacesContext.getCurrentInstance().addMessage(composantId, new FacesMessage(message));
	}

	/**
	 * envoie message JSF de type INFO. Ce message est récupéré dans un bundle
	 * de message JSF.
	 *
	 * @param bundleVar
	 *            variable associée au bundle
	 * @param messageKey
	 *            clé du message à retourner
	 */
	public static void sendBundleMessage(String bundleVar, String messageKey)
	{
		keepMessages();
		String message = getMessageFromBundle(bundleVar, messageKey);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
	}

	/**
	 * Envoie un message de classe INFO à un composant JSF identifié par son
	 * "id".
	 *
	 * @param composantId
	 *            id JSF du composant.
	 * @param severity
	 *            niveau de sévérité.
	 * @param stringFormat
	 *            chaine de formatage (genre String.format()).
	 * @param objects
	 *            objets à insérer dans la chaine de formatage.
	 */
	public static void sendMessage(String composantId, Severity severity, String stringFormat, Object... objects)
	{
		keepMessages();
		String message = String.format(stringFormat, objects);
		FacesContext.getCurrentInstance().addMessage(composantId, new FacesMessage(severity, message, message));
	}

	/**
	 * Envoie un message de classe INFO à un composant PrimeFaces JSF growl,
	 * dont l'id est 'growl'.
	 * 
	 * @param stringFormat
	 *            chaine de formatage (genre String.format()).
	 * @param objects
	 *            objets à insérer dans la chaine de formatage.
	 */
	public static void sendGrowlMessage(String stringFormat, Object... objects)
	{
		JsfUtils.sendGrowlMessage(FacesMessage.SEVERITY_INFO, stringFormat, objects);
	}

	public static void sendGrowlMessage(final Severity pSeverity, String stringFormat, Object... objects)
	{
		keepMessages();
		String message = String.format(stringFormat, objects);
		FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(pSeverity, message, message));
	}

	/**
	 * Dépose une instance d'objet en fonction d'une clé (String) dans le scope
	 * FLASH de JSF 2.
	 *
	 * @param key
	 *            clé associée à l'objet qui est placé dans le flashScope.
	 * @param data
	 *            objet à placer dans le flashScope.
	 */
	public static void putInFlashScope(String key, Object data)
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		Flash flash = ctx.getExternalContext().getFlash();
		flash.put(key, data);
	}

	/**
	 * Récupère l'instance d'un objet en fonction d'une clé (String) dans le
	 * scope FLASH de JSF 2.
	 *
	 * @param key
	 *            clé associée à l'objet qui est placé dans le flashScope.
	 * @return objet trouvé dans le flashScope.
	 */
	public static Object getFromFlashScope(String key)
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		Flash flash = ctx.getExternalContext().getFlash();
		return flash.get(key);
	}

	/**
	 * retourne un message déclaré dans un fichier de bundle de messages
	 * (properties) JSF. Exemple de déclaration d'un bundle dans
	 * faces-config.xml :
	 * 
	 * &lt;application&gt; &lt;resource-bundle&gt;
	 * &lt;base-name>com.appwhite.view.messages.messages&lt;/base-name&gt;
	 * &lt;var&gt;msg&lt;/var&gt; &lt;/resource-bundle&gt; &lt;/application&gt;
	 * 
	 * @param bundleVar
	 *            variable associée au bundle
	 * @param messageKey
	 *            clé du message à retourner
	 * @return message en clair défini dans le bundle
	 */
	public static String getMessageFromBundle(String bundleVar, String messageKey)
	{
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, bundleVar);
		if (bundle == null)
		{
			String message = String.format("%s : Bundle non trouvé : %s", messageKey, bundleVar);
			System.err.println(message);
			return message;
		}
		return bundle.getString(messageKey);
	}

	/**
	 * Retourne la valeur du paramètre HTTP
	 * 
	 * @param parameterName
	 * 		nom du paramètre HTTP
	 * @return
	 * 		valeur du paramètre HTTP
	 */
	public static String getHttpParameter(String parameterName)
	{
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return request.getParameter(parameterName);
	}

	/**
	 * retourne les valeurs du paramètre HTTP passé en paramètre.
	 * 
	 * @param parameterName
	 * 		paramètre HTTP à récupérer.
	 * @return
	 * 		valeurs du paramètre HTTP.
	 */
	public static String[] getHttpParameters(String parameterName)
	{
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return request.getParameterValues(parameterName);
	}
	
	/**
	 * encode une chaine de caractère en une version pour URL HTTP.
	 * 
	 * @param value
	 * 		chaine à encoder.
	 * @return
	 * 		chaine encodée.
	 * @throws UnsupportedEncodingException 
	 */
	public static String encode(final String value)
	{
		try
		{
			return URLEncoder.encode(value, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * redirige vers une autre page.
	 * 
	 * 
	 * @param url
	 * 		adresse de redirection.
	 */
	public static void sendRedirect(String url)
	{
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		try
		{
			response.sendRedirect(url);
			facesContext.responseComplete();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}	
	}
	
	/**
	 * place un attribut en session.
	 * 
	 * @param key
	 * @param value
	 */
	public static void putInSession(String key, Object value)
	{
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.getExternalContext().getSessionMap().put(key, value);
	}
	
	/**
	 * récupère un attribut depuis la session.
	 * 
	 * @param key
	 * @return
	 */
	public static Object getFromSession(String key)
	{
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return facesContext.getExternalContext().getSessionMap().get(key);
	}
	
	
	
}
