/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.itsm.workbench;

import static cn.zhuatech.itsm.workbench.WorkbenchInput.*;

import java.math.*;
import java.util.*;
import org.springframework.stereotype.Service;

/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@Service
public class AiWorkbenchService {
  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  public Map<String, Object> execute(Map<String, Object> r) {
    validate(r);

    keys(r, "failed", "services");
    Map<String, Map<String, Object>> nodes = new TreeMap<>();
    Map<String, List<String>> reverse = new HashMap<>();
    for (var s : rows(r, "services", 1, 200)) {
      keys(s, "id", "dependsOn", "critical");
      String id = id(s, "id");
      if (nodes.putIfAbsent(id, s) != null) throw new IllegalArgumentException("服务编号重复");
      flag(s, "critical");
      var deps = strings(s, "dependsOn", 0, 100);
      if (new HashSet<>(deps).size() != deps.size() || deps.contains(id))
        throw new IllegalArgumentException("服务依赖重复或自引用");
    }
    for (var s : nodes.values())
      for (String d : strings(s, "dependsOn", 0, 100)) {
        if (!nodes.containsKey(d)) throw new IllegalArgumentException("未知依赖服务");
        reverse.computeIfAbsent(d, k -> new ArrayList<>()).add(s.get("id").toString());
      }
    reverse.values().forEach(Collections::sort);
    var failures = new TreeSet<>(strings(r, "failed", 1, 100));
    if (!nodes.keySet().containsAll(failures)) throw new IllegalArgumentException("未知故障源");
    Map<String, List<String>> paths = new LinkedHashMap<>();
    Deque<String> queue = new ArrayDeque<>();
    for (String id : failures) {
      paths.put(id, List.of(id));
      queue.add(id);
    }
    while (!queue.isEmpty()) {
      String id = queue.remove();
      for (String dependent : reverse.getOrDefault(id, List.of()))
        if (!paths.containsKey(dependent)) {
          var path = new ArrayList<>(paths.get(id));
          path.add(dependent);
          paths.put(dependent, List.copyOf(path));
          queue.add(dependent);
        }
    }
    List<Map<String, Object>> out = new ArrayList<>();
    int critical = 0;
    for (var e : paths.entrySet()) {
      boolean c = flag(nodes.get(e.getKey()), "critical");
      if (c) critical++;
      out.add(
          Map.of(
              "id",
              e.getKey(),
              "sourceFailure",
              failures.contains(e.getKey()),
              "critical",
              c,
              "path",
              e.getValue()));
    }
    return Map.of(
        "impacted",
        out,
        "affectedCount",
        out.size(),
        "criticalAffected",
        critical,
        "automaticChange",
        false);
  }
}
