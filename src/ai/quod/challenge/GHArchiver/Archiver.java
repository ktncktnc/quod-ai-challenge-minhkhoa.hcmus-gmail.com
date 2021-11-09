package ai.quod.challenge.GHArchiver;

import ai.quod.challenge.Utils.Parser;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;

public class Archiver {
    HashMap<Long, Event> events;

    public Archiver() {
        events = new HashMap<>();
    }

    public void put(Event event){
        events.put(event.id, event);
    }

    public Event get(Long id){
        return events.get(id);
    }

    public void parseAndPut(String line){
        Event event = Parser.parse(line);
        event.handle();
        put(event);
    }

    public void fromFile(String jsonPath){
        try (Stream<String> stream = Files.lines(Paths.get(jsonPath))) {
            stream.forEachOrdered(this::parseAndPut);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public String toString() {
        return "Archiver{" +
                "events=" + events.size() +
                '}';
    }
}
