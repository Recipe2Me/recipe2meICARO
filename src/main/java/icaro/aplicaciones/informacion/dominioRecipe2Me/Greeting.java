package icaro.aplicaciones.informacion.dominioRecipe2Me;

public class Greeting {

    private String content;
    private Recipe recipe;

    public Greeting(String content) {
        this.content = content;
    }

    public Greeting(String message, Recipe recipe) {
		this.content = message;
		this.recipe = recipe;
	}

	public String getContent() {
        return content;
    }
    
    public Recipe getRecipe() {
    	return recipe;
    }
    
    public void setRecipe(Recipe recipe) {
    	this.recipe = recipe;
    }

}
