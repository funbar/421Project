import java.util.ArrayList;


public class SQLQuery 
{
	
	private ArrayList<Table> tables;
	private String select;
	private String from;
	private String where;
	
	public SQLQuery()
	{
		tables = new ArrayList<Table>();
		select = "";
		from = "";
		where = "";
	}
	
	public void addTable(Table t)
	{
		if(tables.isEmpty())
		{
			from = "FROM ";
			switch(t){
				case Actor:
					from += "Actor A\n";
					break;
				case Director:
					from += "Director D\n";
					break;
				case Movie:
					from += "Movie M\n";
					break;
				case Oscar:
					from += "Oscar O\n";
					break;
				case Person:
					from += "Person P\n";
					break;
				default:
					break;
			}
		}
		else
		{
			from += "INNER JOIN ";
			switch(t){
				case Actor:
					from += "Actor A ";
					switch(tables.get(tables.size()-1)){
						case Director:
							from += "on D.director_id = A.actor_id\n";
							break;
						case Movie:
							from += "on M.id = A.movie_id\n";
							break;
						case Oscar:
							from += " on O.person_id = A.actor_id and O.movie_id = A.movie_id\n";
							break;
						case Person:
							from += "on P.id = A.actor_id\n";
							break;
						default:
							break;
					}// end tables.last switch
					break;
				case Director:
					from += "Director D ";
					switch(tables.get(tables.size()-1)){
						case Movie:
							from += "on M.id = D.movie_id\n";
							break;
						case Oscar:
							from += "on O.person_id = D.director_id and O.movie_id = D.movie_id\n";
							break;
						case Person:
							from += "on P.id = D.director_id\n";
							break;
						case Actor:
							from += "on A.actor_id = D.director_id\n";
							break;
						default:
							break;
					}// end tables.last switch
					break;
				case Movie:
					from += "Movie M ";
					switch(tables.get(tables.size()-1)){
						case Director:
							from += "on D.movie_id = M.id\n";
							break;
						case Oscar:
							from += "O.movie_id = M.id\n";
							break;
						case Actor:
							from += "A.movie_id = D.director_id\n";
							break;
						default:
							break;
					}// end tables.last switch
					break;
				case Oscar:
					from += "Oscars O ";
					switch(tables.get(tables.size()-1)){
						case Director:
							from += "on D.director_id = O.person_id and D.movie_id = O.movie_id\n";
							break;
						case Movie:
							from += "on M.id = O.movie_id\n";
							break;
						case Person:
							from += "on P.id = O.person_id\n";
							break;
						case Actor:
							from += "on A.actor_id = O.person_id and A.movie_id = O.movie_id\n";
							break;
						default:
							break;
					}// end tables.last switch
					break;
				case Person:
					from += "Person P ";
					switch(tables.get(tables.size()-1)){
						case Director:
							from += "on D.director_id = P.id\n";
							break;
						case Actor:
							from += "on A.actor_id = P.id\n";
							break;
						case Oscar:
							from += "on O.person_id = P.id\n";
							break;
						default:
							break;
					}// end tables.last switch
					break;
				default:
					break;
			}// end t switch
		}// end else
		
		
		
		tables.add(t);
	}
	
	public void setSelect(String s)
	{
		select = "SELECT " + s + "\n";
	}
	
	public void addWhere(String s)
	{
		if(where.isEmpty())
		{
			where = "WHERE " + s;
		}
		else
		{
			where += " and " + s;
		}
	}
	
	public String getQuery()
	{	
		return select + from + where + ";";
	}
	
	public static void main(String[] argv)
	{
		SQLQuery q = new SQLQuery();
		q.addTable(Table.Person);
		q.addTable(Table.Director);
		q.setSelect("count(*)");
		q.addWhere("P.name like\"%kubrick%\"");
		System.out.println(q.getQuery());
	}
}
